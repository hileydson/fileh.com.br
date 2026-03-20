import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TipoContaService, TipoConta } from '../../services/tipo-conta.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-financeiro-config',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './financeiro-config.component.html',
  styleUrls: ['./financeiro-config.component.scss']
})
export class FinanceiroConfigComponent implements OnInit {
  activeTab: string = 'tipos-conta';
  tiposConta: TipoConta[] = [];
  loading: boolean = false;
  showModal: boolean = false;
  isEditing: boolean = false;
  saving: boolean = false;
  
  currentTipoConta: TipoConta = {
    nome: '',
    entidadeId: 0
  };

  entidadeId: number = 0;

  constructor(
    private tipoContaService: TipoContaService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx) {
      this.entidadeId = ctx.entidadeId || 0;
      this.carregarTiposConta();
    }
  }

  carregarTiposConta(): void {
    this.loading = true;
    this.tipoContaService.listarPorEntidade(this.entidadeId).subscribe({
      next: (data) => {
        this.tiposConta = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar tipos de conta', err);
        this.loading = false;
      }
    });
  }

  switchTab(tab: string): void {
    this.activeTab = tab;
  }

  openModal(item?: TipoConta): void {
    this.isEditing = !!item;
    if (item) {
      this.currentTipoConta = { ...item };
    } else {
      this.currentTipoConta = {
        nome: '',
        entidadeId: this.entidadeId
      };
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  saveItem(): void {
    this.saving = true;
    if (this.isEditing && this.currentTipoConta.id) {
      this.tipoContaService.atualizar(this.currentTipoConta.id, this.currentTipoConta).subscribe({
        next: () => {
          this.carregarTiposConta();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao atualizar tipo de conta', err);
          this.saving = false;
        }
      });
    } else {
      this.tipoContaService.salvar(this.currentTipoConta).subscribe({
        next: () => {
          this.carregarTiposConta();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao salvar tipo de conta', err);
          this.saving = false;
        }
      });
    }
  }

  deleteItem(id?: number): void {
    if (id && confirm('Tem certeza que deseja excluir este tipo de conta?')) {
      this.tipoContaService.excluir(id).subscribe({
        next: () => this.carregarTiposConta(),
        error: (err) => console.error('Erro ao excluir tipo de conta', err)
      });
    }
  }
}
