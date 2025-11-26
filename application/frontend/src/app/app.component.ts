import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthenticationService } from './core/services/authenticationService';
import { Role } from './core/models/role.enum';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgIf],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  isLoggedIn: boolean = false;
  isWorker: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  ngOnInit() {
    this.authService.isLoggedIn$.subscribe(status => {
      this.isLoggedIn = status;
    });

    this.authService.isWorker$.subscribe(isWorker => {
      this.isWorker = isWorker;
    });
  }

  title = 'Onik';

  logOut(): void {
   this.authService.logout();
  }

  goToPanel(): void {
    this.router.navigate(['/check-tickets']);
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }

  goToHome(): void {
    this.router.navigate(['/']);
  }
}