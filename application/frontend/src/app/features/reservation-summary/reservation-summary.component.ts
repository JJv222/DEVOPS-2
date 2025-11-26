import { Component } from '@angular/core';
import { Seat } from '../../core/models/seat.model';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ticketService } from '../../core/services/ticketservice';
import { TicketCreateResponse } from '../../core/models/summaryDao/ticketCreateResponse.model';
import { Screening } from '../../core/models/screening';

@Component({
  selector: 'app-reservation-summary',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reservation-summary.component.html',
  styleUrls: ['./reservation-summary.component.css']
})
export class ReservationSummaryComponent {
  readonly STANDARD_PRICE = 21.90;
  readonly HIGHT_PRICE = 34.99;
  readonly HIGHT_PRICE_ROW = 3;


  screeningId: number | undefined;
  screening: Screening | undefined;
  selectedSeats: Seat[] = [];
  summaryPrice: number = 0;
  reservationErrorMessage: string | undefined;

  constructor(
    private router: Router,
    private ticketService: ticketService
  ) {
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras.state as {
      screeningId: number,
      selectedSeats: Seat[],
      screening:Screening
    };
    if (state) {
      this.selectedSeats = state.selectedSeats;
      this.screeningId = state.screeningId;
      this.calculateTotalPrice();
      this.screening = state.screening;
    }
  }

  calculateTotalPrice(): void {
    if (!Array.isArray(this.selectedSeats)) {
      this.selectedSeats = [];
    }
    console.log('Calculating total price for selected seats:', this.selectedSeats);
    this.selectedSeats.forEach(seat => {
      if (seat.rowNumber >= this.HIGHT_PRICE_ROW) {
        this.summaryPrice += this.HIGHT_PRICE;
      } else {
        this.summaryPrice += this.STANDARD_PRICE;
      }
    })
  }

  confirmReservation() {
    this.ticketService.makeReservation(this.screeningId!, this.selectedSeats).subscribe({
      next: (response) => {
        console.log('Reservation successful:', response);

        this.router.navigate(
          ['/reservation-summary-result'],
          {
            state: {
              reservationCodes: response,
              screening : this.screening,
            }
          }
        );
      },
      error: (error) => {
        console.error('Reservation failed:', error);
        this.reservationErrorMessage = "wystąpił błąd podczas rezerwacji miejsc. Proszę spróbować ponownie.";
      }
    });
  }
  goBack(){
    this.router.navigate(['/screening', this.screeningId]);
  }

  getSeatPrice(seat: Seat): number {
    return seat.rowNumber >= this.HIGHT_PRICE_ROW ? this.HIGHT_PRICE : this.STANDARD_PRICE;
  }

}