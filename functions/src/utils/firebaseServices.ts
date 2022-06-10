import {initializeApp} from 'firebase-admin/app';
import {getStorage} from 'firebase-admin/storage';
import {getFirestore} from 'firebase-admin/firestore';
import {once} from './functions';

export const app = once(() => initializeApp());
export const storage = once(() => getStorage(app()));
export const db = once(() => getFirestore(app()));

db().settings({
  ignoreUndefinedProperties: true,
});
