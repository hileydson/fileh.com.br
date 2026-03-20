import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SubUsuarioService, SubUsuario } from '../../services/subusuario.service';
import { AuthService } from '../../services/auth.service';
import { EntidadeService } from '../../services/entidade.service';

@Component({
  selector: 'app-subusuario-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './subusuario-list.component.html'
})
export class SubUsuarioListComponent implements OnInit {
  usuarios: SubUsuario[] = [];
  entidades: any[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentUsuario: SubUsuario = this.getEmptyUsuario();

  tenantId: number = 0;

  constructor(
    private subUsuarioService: SubUsuarioService,
    private authService: AuthService,
    private entidadeService: EntidadeService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.tenantId) {
      this.tenantId = ctx.tenantId;
      this.loadUsuarios();
      this.loadEntidades();
    }
  }

  loadUsuarios(): void {
    this.loading = true;
    this.subUsuarioService.getAllByTenant(this.tenantId).subscribe({
      next: (data) => {
        this.usuarios = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar usuários', err);
        this.loading = false;
      }
    });
  }

  loadEntidades(): void {
    this.entidadeService.getAllByTenant(this.tenantId).subscribe(data => {
      this.entidades = data;
    });
  }

  openModal(usuario?: SubUsuario): void {
    if (usuario) {
      this.isEditing = true;
      this.currentUsuario = { ...usuario };
    } else {
      this.isEditing = false;
      this.currentUsuario = this.getEmptyUsuario();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentUsuario = this.getEmptyUsuario();
  }

  saveUsuario(): void {
    this.saving = true;
    
    if (this.isEditing) {
      this.subUsuarioService.update(this.currentUsuario.id!, this.currentUsuario).subscribe({
        next: () => {
          this.loadUsuarios();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao atualizar', err);
          this.saving = false;
        }
      });
    } else {
      this.subUsuarioService.create(this.tenantId, this.currentUsuario).subscribe({
        next: () => {
          this.loadUsuarios();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao criar', err);
          this.saving = false;
        }
      });
    }
  }

  deleteUsuario(id?: number): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir este usuário?')) {
      this.subUsuarioService.delete(id).subscribe({
        next: () => this.loadUsuarios(),
        error: (err) => console.error('Erro ao excluir', err)
      });
    }
  }

  private getEmptyUsuario(): SubUsuario {
    return {
      nome: '',
      login: '',
      moduloVenda: false,
      isAdm: false,
      moduloCaixa: false,
      entidadeId: undefined
    };
  }
}
