import * as fns from 'firebase-functions';
import {storage} from './utils/firebaseServices';

interface UploadFilePayload {
  path: string;
  file: Buffer;
}

export const uploadFile = fns.https.onCall(async (data: UploadFilePayload) => {
  const {path, file} = data;
  const bucket = storage().bucket();
  const fileRef = bucket.file(path);

  return fileRef.save(file);
});

export * as aggregation from './aggregation';
