import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Screening } from "../models/screening";
import { Observable } from "rxjs";
import { Seat } from "../models/seat.model";
import { SeatSummaryDao } from "../models/summaryDao/seatSummaryDao.model";
import { TicketCreateResponse } from "../models/summaryDao/ticketCreateResponse.model";
import { apiBase } from "./apiBase";
import { TicketInfo } from "../models/ResponseDto/TicketInfoResponse";

@Injectable({
  providedIn: 'root'
})
export class ticketService extends apiBase {

  protected override createApiPrefix(): string {
    return 'ticket';
  }

  constructor(private readonly http: HttpClient) {
    super();
  }

  makeReservation(screeningId: number, selectedSeats: Seat[]): Observable<TicketCreateResponse[]> {
    const body: SeatSummaryDao[] = selectedSeats.map(seat => ({
      screeningId: screeningId,
      seatId: seat.id
    }));
    console.log('Making reservation with body:', body);
    return this.http.post<TicketCreateResponse[]>(`${this.apiPrefix}/bulk`, body);
  }

  checkTicket(code: string): Observable<TicketInfo> {
    const head = this.getAuthHeader();
    return this.http.get<TicketInfo>(
      `${this.apiPrefix}/code?code=${code}`,
      { headers: head }
    )
  }

  updateTicketStatus(ticketId: number): Observable<TicketInfo> {
    const head = this.getAuthHeader();
    return this.http.patch<TicketInfo>(
      `${this.apiPrefix}?ticketId=${ticketId}`,
      {},
      { headers: head }
    );
  }


  private getAuthHeader(): HttpHeaders {
    const user = localStorage.getItem('user');
    const token = user ? JSON.parse(user).token : null;
    if (!token) {
      throw new Error("Brak tokena. Zaloguj się ponownie.");
    }
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }


}