import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, forkJoin, switchMap, of } from 'rxjs';
import { FilmScheduler } from '../models/schedulerDao/filmScheduler.model';
import { apiBase } from './apiBase';


@Injectable({
  providedIn: 'root'
})
export class FilmService extends apiBase {

  protected override createApiPrefix(): string {
    return 'film';
  }

  constructor(private readonly http: HttpClient) {
    super();
  }

  getFilmByDate(date: Date): Observable<FilmScheduler[]> {
    const formattedDate = date.toISOString().slice(0, 10);
    return this.http.get<FilmScheduler[]>(`${this.apiPrefix}/repertoire?date=${formattedDate}`).pipe(
      switchMap(films => {
        if(films.length === 0) {
          return of([]);
        }
        const filmPictures$ = films.map(film =>
          this.getFilmPicture(film.image).pipe(
            map(blob => {
              film.pictureUrl = URL.createObjectURL(blob);
              return film;
            })
          )
        );
        return forkJoin(filmPictures$);
      })
    );
  }


  getFilmPicture(filmPictureId: string): Observable<Blob> {
    return this.http.get(`${this.apiPrefix}/image?image=${filmPictureId}`, { responseType: 'blob' });
  }
}
