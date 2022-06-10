import { collection, storage, userAdminRef } from '../services/firebase'
import type { Media } from '../../../types/schema';
import { WriteBatch } from 'firebase-admin/firestore';

interface Options {
  batch?: WriteBatch;
  metadata?: Partial<Media>;
}

export default async (data: string | Buffer, { batch, metadata }: Options = {}) => {
  const docRef = collection.Media.doc();
  const savePath = `/content/${docRef.id}`;
  const bucket = storage.bucket();
  const file = bucket.file(savePath);
  const attrs = {
    ...metadata,
    src: `gs://${bucket.name}${savePath}`,
    uploadedBy: userAdminRef,
    createdAt: new Date,
    updatedAt: new Date,
  };

  await file.save(data);
  await file.makePublic();

  if (batch) batch.create(docRef, attrs);
  else await docRef.set(attrs);

  return docRef;
}
