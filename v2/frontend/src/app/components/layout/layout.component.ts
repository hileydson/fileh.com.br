import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, FormsModule],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {
  entidadeNome: string = 'Painel Administrativo';
  username: string = '';
  nome: string = '';
  isAdmin: boolean = false;
  isVendas: boolean = false;
  isFluxoCaixa: boolean = false;
  isDefaultPassword: boolean = false;

  showUserMenu = false;
  showProfileModal = false;
  showPasswordModal = false;

  profileData = { nome: '', msgFooter: '' };
  passwordData = { newPassword: '', confirmPassword: '' };
  saving = false;
  
  constructor(private authService: AuthService, private router: Router) {
    const ctx = this.authService.getAuthContext();
    if (ctx) {
      if (ctx.entidadeNome) {
        this.entidadeNome = ctx.entidadeNome;
      }
      this.username = ctx.username || '';
      this.nome = ctx.nome || '';
      this.isAdmin = ctx.roles && ctx.roles.includes('ROLE_ADMIN');
      this.isVendas = ctx.roles && (ctx.roles.includes('ROLE_ADMIN') || ctx.roles.includes('ROLE_VENDAS'));
      this.isFluxoCaixa = ctx.roles && (ctx.roles.includes('ROLE_ADMIN') || ctx.roles.includes('ROLE_FLUXO_CAIXA'));
      this.isDefaultPassword = !!ctx.isDefaultPassword;
      this.profileData = {
        nome: this.nome,
        msgFooter: ctx.msgFooter || ''
      };
    }
  }

  toggleUserMenu(): void {
    this.showUserMenu = !this.showUserMenu;
  }

  openProfileModal(): void {
    const ctx = this.authService.getAuthContext();
    this.profileData = {
      nome: ctx?.nome || '',
      msgFooter: ctx?.msgFooter || ''
    };
    this.showProfileModal = true;
    this.showUserMenu = false;
  }

  openPasswordModal(): void {
    this.passwordData = { newPassword: '', confirmPassword: '' };
    this.showPasswordModal = true;
    this.showUserMenu = false;
  }

  saveProfile(): void {
    this.saving = true;
    this.authService.updateProfile({ 
      nome: this.profileData.nome, 
      msgFooter: this.profileData.msgFooter 
    }).subscribe({
      next: () => {
        this.nome = this.profileData.nome;
        this.showProfileModal = false;
        this.saving = false;
      },
      error: (err) => {
        console.error('Erro ao atualizar perfil', err);
        this.saving = false;
      }
    });
  }

  savePassword(): void {
    if (this.passwordData.newPassword !== this.passwordData.confirmPassword) {
      alert('As senhas não conferem!');
      return;
    }
    this.saving = true;
    this.authService.updateProfile({ senha: this.passwordData.newPassword }).subscribe({
      next: () => {
        this.isDefaultPassword = false;
        this.showPasswordModal = false;
        this.saving = false;
        alert('Senha alterada com sucesso!');
      },
      error: (err) => {
        console.error('Erro ao mudar senha', err);
        this.saving = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
