import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FluxoCaixaService, FluxoCaixa } from '../../services/fluxo-caixa.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-fluxo-caixa-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './fluxo-caixa-list.component.html',
  styleUrls: ['./fluxo-caixa-list.component.scss']
})
export class FluxoCaixaListComponent implements OnInit {
  registros: FluxoCaixa[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentRegistro: FluxoCaixa = this.getEmptyRegistro();

  // Context ID fetched on init
  entidadeId: number = 0;

  constructor(
    private fluxoCaixaService: FluxoCaixaService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
        this.entidadeId = ctx.entidadeId;
        this.loadRegistros();
    }
  }

  loadRegistros(): void {
    this.loading = true;
    this.fluxoCaixaService.getAllByEntidade(this.entidadeId).subscribe({
      next: (data) => {
        this.registros = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar fluxo de caixa', err);
        this.loading = false;
      }
    });
  }

  openModal(registro?: FluxoCaixa): void {
    if (registro) {
      this.isEditing = true;
      this.currentRegistro = { ...registro };
    } else {
      this.isEditing = false;
      this.currentRegistro = this.getEmptyRegistro();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentRegistro = this.getEmptyRegistro();
  }

  saveRegistro(): void {
    this.saving = true;
    this.currentRegistro.entidadeId = this.entidadeId;

    if (this.isEditing) {
      this.fluxoCaixaService.update(this.currentRegistro.id!, this.currentRegistro).subscribe({
        next: () => {
          this.loadRegistros();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao atualizar', err);
          this.saving = false;
        }
      });
    } else {
      this.fluxoCaixaService.create(this.currentRegistro).subscribe({
        next: () => {
          this.loadRegistros();
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

  deleteRegistro(id?: number): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir este registro do fluxo de caixa?')) {
      this.fluxoCaixaService.delete(id).subscribe({
        next: () => this.loadRegistros(),
        error: (err) => console.error('Erro ao excluir', err)
      });
    }
  }

  formatDateBR(isoString: string | undefined): string {
    if (!isoString) return 'N/A';
    const parts = isoString.split('-');
    if (parts.length === 3) {
      return `${parts[2]}/${parts[1]}/${parts[0]}`;
    }
    return isoString;
  }

  calcularSaldoVisual(): number {
      return this.registros.reduce((acc, curr) => {
          return curr.tipo === 'E' ? acc + curr.valor : acc - curr.valor;
      }, 0);
  }

  private getEmptyRegistro(): FluxoCaixa {
    const today = new Date().toISOString().split('T')[0];
    return {
      descricao: '',
      entidadeId: this.entidadeId,
      valor: 0,
      tipo: 'E',
      dataCadastro: today,
      formaPagamento: 'Dinheiro'
    };
  }
}
