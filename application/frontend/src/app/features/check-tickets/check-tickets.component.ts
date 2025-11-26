import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TicketInfo } from '../../core/models/ResponseDto/TicketInfoResponse';
import { HttpHeaders } from '@angular/common/http';
import { ticketService } from '../../core/services/ticketservice';
import { TicketStatus } from '../../core/models/ticket-status.enum';
import { debounceTime, distinctUntilChanged, Subscription } from 'rxjs';

@Component({
  selector: 'app-tickets-validator-panel',
  standalone: true,
  imports: [CommonModule, FormsModule,ReactiveFormsModule],
  templateUrl: './check-tickets.component.html',
  styleUrls: ['./check-tickets.component.css']
})
export class TicketsValidatorPanelComponent {
  readonly STATUS = TicketStatus.ACTIVE;
  ticketInfo: TicketInfo | null = null;
  responseErrorMessage: string | null = null;
  ticketCodeControl = new FormControl('');

  constructor(private http: HttpClient, private service: ticketService) {
     this.ticketCodeControl.valueChanges
      .pipe(
        debounceTime(400),
      )
      .subscribe(code => {
          this.checkTicket(code!.trim());
      });
  }

  checkTicket(code: string): void {
    this.service.checkTicket(code)
      .subscribe({
        next: (res) => {
          this.ticketInfo = res;
          this.responseErrorMessage = null;
        },
        error: (err) => {
          this.responseErrorMessage = 'Nie znaleziono biletu o podanym kodzie.';
          this.ticketInfo = null;
        }
      });
  }

  changeTicketStatus() {
    console.log('Changing ticket status to:', this.ticketInfo?.id);
    if (!this.ticketInfo) return;
    this.service.updateTicketStatus(this.ticketInfo.id)
      .subscribe({
        next: (updatedTicket) => {
          this.ticketInfo!.status = updatedTicket.status;
          this.responseErrorMessage = null;
        },
        error: (err) => {
          this.responseErrorMessage = 'Błąd podczas zmiany statusu biletu.';
        }
      });
  }



  getStatusClass(status: TicketStatus): string {
    switch (status) {
      case TicketStatus.ACTIVE:
        return 'status-active';
      case TicketStatus.USED:
        return 'status-used';
      default:
        return '';
    }
  }

}
