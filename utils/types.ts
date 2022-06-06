export interface Address {
  province?: string;
  city?: string;
  addresses?: string;
}

export interface VisitNumber {
  count: number;
  timeUnit: 'day' | 'week' | 'month' | 'year';
}

export interface Place {
  title: string;
  imgSrc: string[];
  address: Address;
  visits: VisitNumber;
  ratings: number;
  reviewCount: number;
  budgetRange: [number, number];
  taskItems: string[];
  desc: string;
  keywords: string;
  submittedBy: string;
  developedBy: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface PlaceReview {
  rating: number;
  comment: string;
  media?: string[];
  createdAt: Date;
  updatedAt: Date;
}
