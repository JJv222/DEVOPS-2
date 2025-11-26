import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { inject } from "@angular/core";
import { FilmService } from "../services/FilmsService";
import { FilmScheduler } from "../models/schedulerDao/filmScheduler.model";

export const filmResolver: ResolveFn<FilmScheduler[]> = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
) => {
  const date = new Date();
  const result = inject(FilmService).getFilmByDate(date);
  result.subscribe(data => console.log('Resolver data:', data));
  return result;
};
