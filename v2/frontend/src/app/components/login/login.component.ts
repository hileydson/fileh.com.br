import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials = { username: '', password: '' };
  loading = false;
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/clientes']);
    }
  }

  onSubmit(): void {
    if (!this.credentials.username || !this.credentials.password) return;
    
    this.loading = true;
    this.errorMessage = '';
    
    this.authService.login(this.credentials).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/clientes']);
      },
      error: err => {
        this.loading = false;
        this.errorMessage = err.error?.message || 'Erro ao efetuar login. Verifique suas credenciais.';
      }
    });
  }
}
