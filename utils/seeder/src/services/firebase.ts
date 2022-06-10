import { ServiceAccount } from 'firebase-admin';
import { initializeApp, cert } from 'firebase-admin/app';
import { CollectionReference, getFirestore } from 'firebase-admin/firestore';
import { getStorage } from 'firebase-admin/storage'
import type { Media, Place, User } from '../../../types/schema';
import serviceAccount from '../../service-account.json';

export const fb = initializeApp({
  credential: cert(serviceAccount as ServiceAccount),
  storageBucket: `${process.env.STORAGE_BUCKET_NAME}.appspot.com`,
});

export const db = getFirestore(fb);

db.settings({
  ignoreUndefinedProperties: true,
});

export const storage = getStorage(fb);

export const collection = {
  User: db.collection('User') as CollectionReference<User>,
  Media: db.collection('Media') as CollectionReference<Media>,
  Place: db.collection('Place') as CollectionReference<Place>,
  _seeder: db.collection('_seeder'),
};

export const userAdminRef = collection.User.doc('Admin');
