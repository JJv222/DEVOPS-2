import { Screening } from "../screening";

export interface FilmScheduler {
  id: number;
  title: string;
  description: string;
  image: string;
  pictureUrl: string|null;
  screenings: Screening[];
}