import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {
  entidadeNome: string = 'Painel Administrativo';
  username: string = '';
  isAdmin: boolean = false;
  isVendas: boolean = false;
  isFluxoCaixa: boolean = false;
  
  constructor(private authService: AuthService, private router: Router) {
    const ctx = this.authService.getAuthContext();
    if (ctx) {
      if (ctx.entidadeNome) {
        this.entidadeNome = ctx.entidadeNome;
      }
      this.username = ctx.username || '';
      this.isAdmin = ctx.roles && ctx.roles.includes('ROLE_ADMIN');
      this.isVendas = ctx.roles && (ctx.roles.includes('ROLE_ADMIN') || ctx.roles.includes('ROLE_VENDAS'));
      this.isFluxoCaixa = ctx.roles && (ctx.roles.includes('ROLE_ADMIN') || ctx.roles.includes('ROLE_FLUXO_CAIXA'));
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
