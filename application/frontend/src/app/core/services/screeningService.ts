import { Injectable } from "@angular/core";
import { apiBase } from "./apiBase";
import { HttpClient } from "@angular/common/http";
import { Screening } from "../models/screening";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class screeningService extends apiBase {

  protected override createApiPrefix(): string {
    return 'screening';
  }

  constructor(private readonly http: HttpClient) {
    super();
  }

  getScreeningById(screeningId: number): Observable<Screening[]> {  
    return this.http.get<Screening[]>(`${this.apiPrefix}?screeningId=${screeningId}`);
  }
}