import { TicketStatus } from "../ticket-status.enum";


export interface TicketInfo {
  id: number;
  reservationCode: string;
  film: string;
  room: string;
  row: number;
  column: number;
  status: TicketStatus;
}