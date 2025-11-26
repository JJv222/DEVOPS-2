export interface TicketCreateResponse {
  id: number;
  reservationCode: string;
  film: string;
  room: string;
  row: number;
  column: number;
  status: string;
}