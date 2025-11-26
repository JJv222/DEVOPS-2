import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';
import { AuthenticationRequest } from '../models/RequestDto/AuthenticationRequest';
import { AuthenticationResponse } from '../models/ResponseDto/AuthenticationResponse';
import { apiBase } from './apiBase';
import { Role } from '../models/role.enum';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService extends apiBase {
  private _isLoggedIn = new BehaviorSubject<boolean>(this.checkIfLoggedIn());
  private _isWorker = new BehaviorSubject<boolean>(this.checkIfWorker());

  public isLoggedIn$ = this._isLoggedIn.asObservable();
  public isWorker$ = this._isWorker.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    super();
  }

  protected override createApiPrefix(): string {
    return 'auth';
  }

  login(credentials: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiPrefix}/login`, credentials).pipe(
      tap((res: AuthenticationResponse) => {
        localStorage.setItem('user', JSON.stringify(res));
        this._isLoggedIn.next(true);
        this._isWorker.next(res.role === Role.WORKER);
      })
    );
  }
  
logout(): void {
  localStorage.removeItem('user');
  this._isLoggedIn.next(false);
  this._isWorker.next(false);
  this.router.navigate(['/']);
}

  private checkIfLoggedIn(): boolean {
    return !!localStorage.getItem('user');
  }

  private checkIfWorker(): boolean {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user).role === 'Worker' : false;
  }
}
