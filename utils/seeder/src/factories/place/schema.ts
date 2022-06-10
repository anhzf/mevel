export const TourismCategories = ['Taman Hiburan', 'Budaya', 'Cagar Alam', 'Bahari', 'Tempat Ibadah'] as const;

export type TourismCategory = typeof TourismCategories[number];

export interface Tourism {
  Place_Id: number;
  Place_Name: string;
  Description: string;
  Category: TourismCategory;
  City: string;
  Price: number;
  Rating: number;
  Time_Minutes: number;
  /* Json object */
  Coordinate: string;
  Lat: number;
  Long: number;
}
