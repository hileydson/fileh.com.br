import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EntidadeService, Entidade } from '../../services/entidade.service';
import packageInfo from '../../../../package.json';

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
  version = packageInfo.version;
  
  selectingEntity = false;
  entidades: Entidade[] = [];

  constructor(
    private authService: AuthService, 
    private entidadeService: EntidadeService,
    private router: Router
  ) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/']);
    }
  }

  onSubmit(): void {
    if (!this.credentials.username || !this.credentials.password) return;
    
    this.loading = true;
    this.errorMessage = '';
    
    this.authService.login(this.credentials).subscribe({
      next: (res: any) => {
        const isAdmin = res.roles && res.roles.includes('ROLE_ADMIN');
        
        if (isAdmin) {
          this.entidadeService.getAllByTenant(res.tenantId).subscribe({
            next: (data) => {
              this.entidades = data.sort((a, b) => a.nome.localeCompare(b.nome));
              this.selectingEntity = true;
              this.loading = false;
            },
            error: () => {
              this.loading = false;
              this.router.navigate(['/']);
            }
          });
        } else {
          this.loading = false;
          this.router.navigate(['/']);
        }
      },
      error: err => {
        this.loading = false;
        this.errorMessage = err.error?.message || 'Erro ao efetuar login. Verifique suas credenciais.';
      }
    });
  }

  selectEntity(entidadeId: number): void {
    this.loading = true;
    this.authService.switchEntidade(entidadeId).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/']);
      },
      error: () => {
        this.loading = false;
        alert('Erro ao selecionar empresa. Tente novamente.');
      }
    });
  }
}
