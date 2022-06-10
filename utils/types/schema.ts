import type { DocumentReference, GeoPoint } from 'firebase-admin/firestore';

export interface User { }

export interface UserRecommendationPreference { }

export interface UserSettingsPreference { }

export interface Media {
  src: string;
  title?: string;
  desc?: string;
  owner?: string;
  uploadedBy: DocumentReference<User>;
  createdAt: Date;
  updatedAt: Date;
  [key: string]: any;
}

export interface Address {
  province?: string;
  city?: string;
  districts?: string;
  addresses?: string;
  latlong?: GeoPoint;
}

export interface VisitNumber {
  count: number;
  timeUnit: 'day' | 'week' | 'month' | 'year';
}

export type PlaceCategory = 'alam_darat' | 'alam_laut' | 'alam_salju' | 'seni' | 'sports';

export interface Place {
  title: string;
  imgSrc: DocumentReference<Media>[];
  address: Address;
  displayAddress: string;
  mapsUrl?: string;
  visits?: VisitNumber;
  readonly ratingScore?: number;
  readonly reviewCount?: number;
  desc?: string;
  category: PlaceCategory[];
  keywords: string[];
  submittedBy?: DocumentReference<User>;
  developedBy?: string;
  createdAt: Date;
  updatedAt: Date;
  budgetRange?: [number, number];
  taskItems?: string[];
  fromGmaps?: {
    placeId: string;
    fields?: string[];
    [key: string]: any;
  };
  [key: string]: any;
}

export interface PlaceStory {
  rating: number;
  comment?: string;
  media?: DocumentReference<Media>[];
  readonly upvoted?: number;
  readonly downvoted?: number;
  createdAt: Date;
  updatedAt: Date;
}

export interface PlaceStoryVote {
  [userId: string]: boolean;
}
