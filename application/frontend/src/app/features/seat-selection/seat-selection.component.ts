import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Seat } from '../../core/models/seat.model';
import { Screening } from '../../core/models/screening';

@Component({
  selector: 'app-seat-selection',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './seat-selection.component.html',
  styleUrls: ['./seat-selection.component.css']
})
export class SeatSelectionComponent {
  readonly MAX_PLACES_SELECTED = 3;

  seats: Seat[] = [];
  selectedSeatsNumbers: number[] = [];
  selectedSeats: Seat[] = [];
  screening: Screening | undefined;
  rows: Seat[][] = [];
  seatGrid: (Seat | null)[][] = [];


  constructor(
    private route: ActivatedRoute,
    private router: Router) {
    this.screening = this.route.snapshot.data['screening'];

    if (this.screening && Array.isArray(this.screening.seats)) {
      this.seats = this.screening.seats;
      this.generateSeatGrid(this.seats);
    }
  }

  generateSeatGrid(seats: Seat[]) {
    const maxRow = Math.max(...seats.map(s => s.rowNumber));
    const maxSeat = Math.max(...seats.map(s => s.seatNumber));
    const seatsMap = new Map<string, Seat>();
    seats.forEach(seat => {
      seatsMap.set(`${seat.rowNumber}-${seat.seatNumber}`, seat);
    });

    this.seatGrid = [];
    for (let row = 1; row <= maxRow; row++) {
      const rowSeats: (Seat | null)[] = [];
      for (let seatNum = 1; seatNum <= maxSeat; seatNum++) {
        const seat = seatsMap.get(`${row}-${seatNum}`) || null;
        rowSeats.push(seat);
      }
      this.seatGrid.push(rowSeats);
    }
  }


  toggleSeat(seat: Seat): void {
    if (seat.occupied) return;

    else if (this.selectedSeatsNumbers.includes(seat.id)) {
      this.selectedSeatsNumbers = this.selectedSeatsNumbers.filter(id => id !== seat.id);
      this.selectedSeats = this.selectedSeats.filter(s => s.id !== seat.id);
    } else {
      if (this.selectedSeatsNumbers.length >= this.MAX_PLACES_SELECTED) {
        alert(`Możesz zarezerwować maksymalnie ${this.MAX_PLACES_SELECTED} miejsc!`);
        return;
      }
      this.selectedSeats.push(seat);
      this.selectedSeatsNumbers.push(seat.id);
    }
  }

  confirm(): void {
      this.router.navigate(
    ['/reservation-summary'],
    { state: {
      screeningId: this.screening?.id,
      selectedSeats: this.selectedSeats,
      screening: this.screening
    } }
  );
  }
}
