import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent } from './features/login/login.component';
import { RegisterComponent } from './features/register/register.component';
import { ScheduleComponent } from './features/schedule/schedule.component';
import { SeatSelectionComponent } from './features/seat-selection/seat-selection.component';
import { TicketsValidatorPanelComponent } from './features/check-tickets/check-tickets.component';
import { filmResolver } from './core/resolvers/films.resolver';
import { screeningResolver } from './core/resolvers/screening.resolver';
import { ReservationSummaryComponent } from './features/reservation-summary/reservation-summary.component';
import { ReservationSummaryResultComponent } from './features/reservation-summary-result/reservation-summary-result.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    resolve: {
      allFilms: filmResolver
    }
  },
  { 
    path: 'screening/:id', 
    component: SeatSelectionComponent,
    resolve: {
      screening: screeningResolver
    }
  },
  {
    path: 'reservation-summary',
     component: ReservationSummaryComponent 
  },
  { 
    path: 'register', 
    component: RegisterComponent 
  },
  { 
    path: 'login', 
    component: LoginComponent 
  },
  {
    path: 'reservation-summary-result',
    component: ReservationSummaryResultComponent,
  },
  {
    path: 'schedule',
    component: ScheduleComponent,
    resolve: {
      allFilms: filmResolver
    }
  },
  { 
    path: 'check-tickets', 
    component: TicketsValidatorPanelComponent 
  }
];