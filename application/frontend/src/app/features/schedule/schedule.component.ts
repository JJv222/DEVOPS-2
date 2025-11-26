import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { FilmScheduler } from '../../core/models/schedulerDao/filmScheduler.model';
import { FilmService } from '../../core/services/FilmsService';
import { BehaviorSubject, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-schedule',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule, CommonModule],
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent {
  days: { dayLabel: string; dateLabel: string, date: Date }[] = [];
  selectedDate = new BehaviorSubject<Date | null>(null);
  filteredFilms: FilmScheduler[];


  constructor(
    private router: Router,
    private readonly route: ActivatedRoute,
    private readonly filmService: FilmService
  ) {
    this.days = this.generateWeekDays();
    this.filteredFilms = this.route.snapshot.data['allFilms'];
    this.sortScreenings(this.filteredFilms);
    let firstLoad = true;

    this.selectedDate.pipe(
      debounceTime(200),
      distinctUntilChanged((a, b) => a?.toISOString() === b?.toISOString()),
      switchMap(date => {
        if (firstLoad) {
          firstLoad = false;
          return of(this.filteredFilms);
        }
        if (date) {
          return this.filmService.getFilmByDate(date);
        }
        return of([]);
      })
    ).subscribe(films => {
      this.sortScreenings(films);
      this.filteredFilms = films;
    });

  }
  sortScreenings(films: FilmScheduler[]): void {
    films.forEach(film => {
      film.screenings.sort((a, b) => new Date(a.dateTime).getTime() - new Date(b.dateTime).getTime());
    });
  }

  generateWeekDays(): { dayLabel: string; dateLabel: string; date: Date }[] {
    const today = new Date();
    const result: { dayLabel: string; dateLabel: string; date: Date }[] = [];

    for (let i = 0; i < 7; i++) {
      const date = new Date(today);
      date.setDate(today.getDate() + i);

      let dayLabel: string;
      if (i === 0) {
        dayLabel = 'Dzisiaj';
      } else if (i === 1) {
        dayLabel = 'Jutro';
      } else {
        dayLabel = date.toLocaleDateString('pl-PL', { weekday: 'short' });
        dayLabel = dayLabel.charAt(0).toUpperCase() + dayLabel.slice(1);
      }

      const dateLabel = date.toLocaleDateString('pl-PL', { day: 'numeric', month: 'short' });
      result.push({ dayLabel, dateLabel, date });
    }

    return result;
  }


  selectDay(date: Date): void {
    if (this.selectedDate.value?.toDateString() === date.toDateString()) {
      return;
    }
    this.selectedDate.next(date);
  }


  goToScreening(screeningId: number): void {
    this.router.navigate(['/screening', screeningId]);
  }
}
