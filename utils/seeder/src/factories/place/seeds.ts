import { existsSync, mkdirSync, readFileSync, writeFileSync } from 'fs';
import { resolve, } from 'path';
import { GeoPoint, WriteBatch, WriteResult } from 'firebase-admin/firestore';
import axios from 'axios';
import axiosRetry from 'axios-retry';
import { parse } from 'csv-parse/sync';
import chalk from 'chalk';
import { print } from '../../../../debugging';
import { collection, db, userAdminRef } from '../../services/firebase';
import uploadMedia from '../../utils/uploadMedia';
import type { Place } from '../../../../types/schema';
import type { Tourism, TourismCategory } from './schema';

axiosRetry(axios, { retries: 3, retryDelay: (count) => count * 2000 });

interface SeedHistory {
  places: string[]; // IDs
  photos: string[]; // IDs
  updatedAt: Date;
}

const batches = (() => {
  // https://firebase.google.com/docs/firestore/quotas#writes_and_transactions
  const IDLE_TIME = 60000;
  const MAX_OPERATIONS = 200;
  type Batch = { batch: WriteBatch, operations: number; }
  const batchPool: Batch[] = [];
  const newBatch = () => (batchPool.push({ batch: db.batch(), operations: 0 }) && batchPool.at(-1)) as Batch;
  const getBatch = (): Batch => {
    if (batchPool.length) {
      const b = batchPool.at(-1)!;
      if (b.operations < MAX_OPERATIONS) return b;
    }
    return newBatch();
  }

  return {
    get get() {
      return getBatch();
    },
    commit: async () => {
      process.argv.includes('-q') || console.log(`Found ${batchPool.length} batches with ${batchPool.reduce((acc, el) => acc + el.operations, 0)} operations...`);

      const writeResults: WriteResult[] = [];

      for (const b of batchPool) {
        const result = await b.batch.commit();
        batchPool.shift();
        writeResults.push(...result);
      }

      return writeResults;
    },
    create: (...args: Parameters<WriteBatch['create']>) => {
      const b = getBatch();
      const result = b.batch.create(...args);
      b.operations++;
      return result;
    },
    set: (...args: Parameters<WriteBatch['set']>) => {
      const b = getBatch();
      const result = b.batch.set(...args);
      b.operations++;
      return result;
    },
    delete: (...args: Parameters<WriteBatch['delete']>) => {
      const b = getBatch();
      const result = b.batch.delete(...args);
      b.operations++;
      return result;
    },
    update: (...args: Parameters<WriteBatch['update']>) => {
      const b = getBatch();
      const result = b.batch.update(...args);
      b.operations++;
      return result;
    },
  };
})();

const cacheDir = (() => {
  const dir = resolve(__dirname, './.cache');
  if (!existsSync(dir)) mkdirSync(dir);
  return dir;
})();

const getSources = async () => {
  const filePath = resolve(__dirname, './sources/tourism.csv');
  const file = readFileSync(filePath);

  return parse(file, {
    columns: true,
    skip_empty_lines: true,
    trim: true,
    cast: true,
  }) as Tourism[];
};

const inferCategory = (category: TourismCategory): Place['category'] => {
  switch (category) {
    case 'Bahari':
      return ['alam_laut'];
    case 'Budaya':
      return ['seni'];
    case 'Cagar Alam':
      return ['alam_darat'];
    case 'Taman Hiburan':
      return ['seni', 'sports'];
    default:
      return ['alam_darat'];
  }
}

const seedingHistory = (() => {
  const filePath = resolve(cacheDir, 'seedingHistory.json');
  const data: SeedHistory = existsSync(filePath)
    ? JSON.parse(readFileSync(filePath).toString())
    : { places: [], photos: [], updatedAt: new Date() };
  const save = () => writeFileSync(filePath, JSON.stringify(data, null, 2));

  return { data, save };
})();

const detailsCache = (() => {
  const cachePath = resolve(cacheDir, 'places.json');
  let cached: any[] = [];

  const read = () => {
    cached = existsSync(cachePath) ? JSON.parse(readFileSync(cachePath).toString()) : [];
  };
  const find = (identifier: string) => cached.find((p: any) => [p._title, p.place_id].includes(identifier));
  const save = () => {
    const filtered = cached.filter((el) => Object.keys(el).length > 1);
    writeFileSync(cachePath, JSON.stringify(filtered, null, 2));
    read();
  };
  const push = (data: any) => (cached.push(data), save());

  read();

  return {
    find,
    push,
    save,
  }
})();

const photosCache = (() => {
  const save = (id: string, data: string | Buffer) => writeFileSync(resolve(cacheDir, `${id}`), data);
  const find = (id: string) => existsSync(resolve(cacheDir, `${id}`))
    ? readFileSync(resolve(cacheDir, `${id}`)).toString() : null;

  return {
    save,
    find,
  };
})();

const getMoreDetails = async (placeName: string) => {
  console.log(chalk.bold(placeName) + ': getting from cache...');

  let cached = detailsCache.find(placeName);

  if (cached) return (console.log(chalk.bold(placeName) + ': returned from cache!'), cached);

  console.log(chalk.bold(placeName) + ': no cache found, fetching from google...');

  const searchApiUrl = `https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=${encodeURIComponent(placeName)}&inputtype=textquery&language=id&key=${process.env.API_KEY}`;
  const searchRes = await axios.get(searchApiUrl);

  const placeId = searchRes.data.candidates[0]?.place_id;

  if (!placeId) return (console.log(chalk.bold(placeName) + ': no data found from google!'), null);

  console.log(chalk.bold(placeName) + ': finding details from cache...');

  cached = detailsCache.find(placeId);

  if (cached) return (console.log(chalk.bold(placeName) + ': returned from cache!'), cached);

  console.log(chalk.bold(placeName) + ': fetching details from google...');

  const detailsApiUrl = `https://maps.googleapis.com/maps/api/place/details/json?place_id=${encodeURIComponent(placeId)}&language=id&key=${process.env.API_KEY}`;
  const detailsRes = await axios.get(detailsApiUrl);
  const { result } = detailsRes.data;

  detailsCache.push({ _title: placeName, ...result });

  return result;
}

const resolvePlacePhoto = async (photoRef: string) => {
  const cached = photosCache.find(photoRef);

  if (cached) return cached;

  try {
    const placePhotoUrl = `https://maps.googleapis.com/maps/api/place/photo?photo_reference=${encodeURIComponent(photoRef)}&maxwidth=1600&key=${process.env.API_KEY}`;
    const { data } = await axios.get(placePhotoUrl, { responseType: 'arraybuffer' })

    photosCache.save(photoRef, data);
    console.count('Photos from API');
    return data;
  } catch (err) {
    print(err, { depth: 2 });
  }
}

const toFirestoreSchema = (data: Tourism): Place => ({
  title: data.Place_Name,
  imgSrc: [],
  address: {
    latlong: new GeoPoint(data.Lat, data.Long),
  },
  displayAddress: data.City,
  desc: data.Description,
  category: inferCategory(data.Category),
  keywords: [],
  createdAt: new Date(),
  updatedAt: new Date(),
});

const mergeDetails = async (place: Place): Promise<Place> => {
  const details = await getMoreDetails(place.title);

  if (!details) return place;

  const photos = Array.isArray(details.photos) ? (
    console.log(chalk.bold(place.title) + `: downloading and saving ${chalk.bold(details.photos.length)} photos...`),
    await Promise.all(details.photos.map(async (p: any) => {
      const alreadySaved = seedingHistory.data.photos.find((el) => el === p.photo_reference);

      if (alreadySaved) {
        console.log(chalk.bold(place.title) + `: photo ${chalk.bold(p.photo_reference)} already saved!`);
        const snapshot = await collection.Media.where('placeApi_photoReference', '==', p.photo_reference).limit(1).get();
        return snapshot.docs[0];
      }

      const uploaded = await uploadMedia(
        await resolvePlacePhoto(p.photo_reference),
        { batch: batches as WriteBatch, metadata: { placeApi_photoReference: p.photo_reference } }
      );

      seedingHistory.data.photos.push(p.photo_reference);

      return uploaded;
    }).filter(Boolean))
  ) : []

  const merged: Place = {
    ...place,
    mapsUrl: details.url,
    imgSrc: photos,
    address: {
      city: details.address_components?.find((c: any) => c.types.includes('locality'))?.long_name
        || place.address || null,
      province: details.address_components?.find((c: any) => c.types.includes('administrative_area_level_1'))?.long_name
        || place.address || null,
      addresses: details.formatted_address
        || place.address.addresses || null,
      districts: details.address_components?.filter((c: any) => c.types.includes('sublocality')).map((c: any) => c.long_name)
        || place.address.districts || null,
      latlong: (details.geometry?.location && new GeoPoint(details.geometry.location.lat, details.geometry.location.lng))
        || place.address || null,
    },
    displayAddress: details.formatted_address
      || place.address.addresses || null,
    keywords: [...details.types, ...place.keywords],
    submittedBy: userAdminRef,
    fromGmaps: {
      placeId: details.place_id,
      url: details.url,
    }
  }

  return merged;
}

/**
 * @param {number} start inclusive
 * @param {number} end exclusive
 */
export default async (start?: number, end?: number) => {
  const sources = await getSources();
  const places = sources.map(toFirestoreSchema).slice(start, end);
  const placesWithDetails: Place[] = [];

  for (const place of places) {
    placesWithDetails.push(await mergeDetails(place));
  }

  console.log('\nsaving before exit...');

  placesWithDetails.forEach((place) => {
    const alreadySaved = seedingHistory.data.places.find((el) => el === place.fromGmaps?.placeId);

    if (alreadySaved) return console.log(chalk.bold(place.title) + `: place already saved!`);

    const ref = collection.Place.doc();
    batches.create(ref, place);
    if (place.fromGmaps) seedingHistory.data.places.push(place.fromGmaps.placeId);
  });

  await batches.commit();
  seedingHistory.save();
}
