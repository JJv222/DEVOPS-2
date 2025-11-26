import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TicketCreateResponse } from '../../core/models/summaryDao/ticketCreateResponse.model';
import { CommonModule } from '@angular/common';
import { Screening } from '../../core/models/screening';

@Component({
  selector: 'app-reservation-summary-result',
  imports: [CommonModule],
  templateUrl: './reservation-summary-result.component.html',
  styleUrl: './reservation-summary-result.component.css'
})
export class ReservationSummaryResultComponent {
  readonly reservationsResponse: TicketCreateResponse[] = [];
  readonly screening: Screening | undefined;

  constructor(
    public router: Router
  ) {
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras.state as {
      reservationCodes: TicketCreateResponse[],
      screening:Screening,
    };
    if (state) {
      this.reservationsResponse = state.reservationCodes
      this.screening = state.screening;
    }
  }
}
