import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FornecedorService, Fornecedor } from '../../services/fornecedor.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-fornecedor-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './fornecedor-list.component.html',
  styleUrls: ['./fornecedor-list.component.scss']
})
export class FornecedorListComponent implements OnInit {
  fornecedores: Fornecedor[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentFornecedor: Fornecedor = this.getEmptyFornecedor();

  entidadeId: number = 0;

  constructor(
    private fornecedorService: FornecedorService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
        this.entidadeId = ctx.entidadeId;
        this.loadFornecedores();
    }
  }

  loadFornecedores(): void {
    this.loading = true;
    this.fornecedorService.getAllByTenant(this.entidadeId).subscribe({
      next: (data) => {
        this.fornecedores = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar fornecedores', err);
        this.loading = false;
      }
    });
  }

  openModal(fornecedor?: Fornecedor): void {
    if (fornecedor) {
      this.isEditing = true;
      this.currentFornecedor = { ...fornecedor };
    } else {
      this.isEditing = false;
      this.currentFornecedor = this.getEmptyFornecedor();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentFornecedor = this.getEmptyFornecedor();
  }

  saveFornecedor(): void {
    this.saving = true;
    this.currentFornecedor.entidadeId = this.entidadeId;

    if (this.isEditing) {
      this.fornecedorService.update(this.currentFornecedor.id!, this.currentFornecedor).subscribe({
        next: () => {
          this.loadFornecedores();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao atualizar', err);
          this.saving = false;
        }
      });
    } else {
      this.fornecedorService.create(this.currentFornecedor).subscribe({
        next: () => {
          this.loadFornecedores();
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

  deleteFornecedor(id?: number): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir este fornecedor?')) {
      this.fornecedorService.delete(id).subscribe({
        next: () => this.loadFornecedores(),
        error: (err) => console.error('Erro ao excluir', err)
      });
    }
  }

  private getEmptyFornecedor(): Fornecedor {
    return {
      nome: '',
      cnpj: '',
      logradouro: '',
      bairro: '',
      cidade: '',
      cep: '',
      inscricaoEstadual: '',
      inscricaoMunicipal: '',
      contato: ''
    };
  }
}
