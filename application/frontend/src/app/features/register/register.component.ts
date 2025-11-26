import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'] 
})
export class RegisterComponent {
  email = '';
  username = '';
  password = '';
  surname = '';
  showPassword = false;

  constructor(private router: Router) {}

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
  goToLogin(): void {
    this.router.navigate(['/login']);
  }

  register(): void {
    // tu logika rejestracji (np. zapytanie do API)
    alert('Zarejestrowano pomyślnie!');
    this.router.navigate(['/login']);
  }
}
