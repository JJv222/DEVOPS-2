import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../core/services/authenticationService';
import { Role } from '../../core/models/role.enum';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  showPassword: boolean = false;

constructor(private router: Router, private authService: AuthenticationService) {}

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
  gotoRegister(): void {
    this.router.navigate(['/register']);
  }

  login() {
    this.authService.login({ email: this.email, password: this.password }).subscribe({
    next: (res) => {
      localStorage.setItem('user', JSON.stringify({
        username: res.username,
        role: res.role,
        token: res.token
      }));
      
      alert('Zalogowano pomyślnie');

      if (res.role === Role.WORKER) {
        this.router.navigate(['/check-tickets']);
      } else {
        this.router.navigate(['/']);
      }
    },
    error: () => {
      alert('Nieprawidłowe dane logowania');
    }
  });
  }
}