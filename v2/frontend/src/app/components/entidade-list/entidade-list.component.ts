import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EntidadeService, Entidade } from '../../services/entidade.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-entidade-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './entidade-list.component.html',
  styleUrls: ['./entidade-list.component.scss']
})
export class EntidadeListComponent implements OnInit {
  entidades: Entidade[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentEntidade: Entidade = this.getEmptyEntidade();
  searchTerm: string = '';

  tenantId: number = 1;

  constructor(
    private entidadeService: EntidadeService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const token = this.authService.getToken();
    if (token) {
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            this.tenantId = payload.tenantId || 1;
        } catch(e) {}
    }
    this.loadEntidades();
  }

  loadEntidades(): void {
    this.loading = true;
    this.entidadeService.getAllByTenant(this.tenantId).subscribe({
      next: (data) => {
        this.entidades = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar entidades', err);
        this.loading = false;
      }
    });
  }

  onSearch(): void {
    if (!this.searchTerm || this.searchTerm.trim() === '') {
      this.loadEntidades();
      return;
    }

    this.loading = true;
    this.entidadeService.search(this.tenantId, this.searchTerm.trim()).subscribe({
      next: (data) => {
        this.entidades = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro na pesquisa', err);
        this.loading = false;
      }
    });
  }

  openModal(entidade?: Entidade): void {
    if (entidade) {
      this.isEditing = true;
      this.currentEntidade = { ...entidade };
    } else {
      this.isEditing = false;
      this.currentEntidade = this.getEmptyEntidade();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentEntidade = this.getEmptyEntidade();
  }

  saveEntidade(): void {
    this.saving = true;
    this.currentEntidade.usuarioId = this.tenantId;

    if (this.isEditing) {
      this.entidadeService.update(this.currentEntidade.id!, this.currentEntidade).subscribe({
        next: () => {
          this.loadEntidades();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao atualizar', err);
          this.saving = false;
        }
      });
    } else {
      this.entidadeService.create(this.currentEntidade).subscribe({
        next: () => {
          this.loadEntidades();
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

  deleteEntidade(id?: number): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir esta entidade?')) {
      this.entidadeService.delete(id).subscribe({
        next: () => this.loadEntidades(),
        error: (err) => console.error('Erro ao excluir', err)
      });
    }
  }

  private getEmptyEntidade(): Entidade {
    return {
      nome: ''
    };
  }
}
