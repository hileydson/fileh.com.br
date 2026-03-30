import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FluxoCaixaService, FluxoCaixa } from '../../services/fluxo-caixa.service';
import { DateBrPipe } from '../../pipes/date-br.pipe';
import { AuthService } from '../../services/auth.service';
import { FormaPagamentoService, FormaPagamento } from '../../services/forma-pagamento.service';
import { DateInputComponent } from '../shared/date-input/date-input.component';

@Component({
  selector: 'app-fluxo-caixa-list',
  standalone: true,
  imports: [CommonModule, FormsModule, DateBrPipe, DateInputComponent],
  templateUrl: './fluxo-caixa-list.component.html',
  styleUrls: ['./fluxo-caixa-list.component.scss']
})
export class FluxoCaixaListComponent implements OnInit {
  registros: FluxoCaixa[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  
  selectedDate: string = new Date().toISOString().split('T')[0];
  maxDate: string = new Date().toISOString().split('T')[0];
  
  manualTypeSelection = false;
  
  formasPagamento: FormaPagamento[] = [];
  currentRegistro: FluxoCaixa = this.getEmptyRegistro();
  
  @ViewChild('valorInput') valorInput!: ElementRef;

  // Context ID fetched on init
  entidadeId: number = 0;

  constructor(
    private fluxoCaixaService: FluxoCaixaService,
    private authService: AuthService,
    private formaPagamentoService: FormaPagamentoService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
        this.entidadeId = ctx.entidadeId;
        this.currentRegistro = this.getEmptyRegistro();
        this.loadFormasPagamento();
        this.loadRegistros();
        setTimeout(() => this.focusValor(), 500);
    }
  }

  loadFormasPagamento(): void {
    this.formaPagamentoService.getAllByTenant(this.entidadeId, 'CX').subscribe(data => {
        this.formasPagamento = data;
        if (!this.isEditing && this.formasPagamento.length > 0) {
            this.currentRegistro.formaPagamento = this.formasPagamento[0].descricao;
        }
    });
  }

  onDateChange(): void {
    this.loadRegistros();
  }

  loadRegistros(): void {
    this.loading = true;
    this.fluxoCaixaService.getAllByEntidade(this.entidadeId, this.selectedDate).subscribe({
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
    if (this.saving) return;
    
    // Auto-detect type based on sign
    if (this.currentRegistro.valor < 0) {
      this.currentRegistro.tipo = 'SA';
      this.currentRegistro.valor = Math.abs(this.currentRegistro.valor);
    } else if (this.currentRegistro.valor > 0 && this.currentRegistro.tipo === 'SA' && !this.isEditing) {
      // If it's a new entry and user explicitly has 'SA' selected but entered a positive number,
      // it should remain 'SA' (case where they use the E/S toggle instead of the sign).
      // If it's positive and they didn't toggle 'SA', it's 'EN' (default).
    }

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
          const savedType = this.currentRegistro.tipo;
          const savedFP = this.currentRegistro.formaPagamento;
          
          this.currentRegistro = this.getEmptyRegistro();
          
          // If type was NOT manually selected, reset to 'E' (default from getEmptyRegistro)
          // otherwise persist the manual selection.
          if (this.manualTypeSelection) {
            this.currentRegistro.tipo = savedType;
          } else {
            this.currentRegistro.tipo = 'EN';
          }
          
          this.currentRegistro.formaPagamento = savedFP;
          this.saving = false;
          setTimeout(() => this.focusValor(), 100);
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

  // Formatter moved to DateBrPipe

  calcularSaldoVisual(): number {
      return this.registros.reduce((acc, curr) => {
          return curr.tipo === 'EN' ? acc + curr.valor : acc - curr.valor;
      }, 0);
  }

  private getEmptyRegistro(): FluxoCaixa {
    return {
      descricao: '',
      entidadeId: this.entidadeId,
      valor: 0,
      tipo: 'EN',
      dataCadastro: this.selectedDate,
      formaPagamento: this.formasPagamento.length > 0 ? this.formasPagamento[0].descricao : 'Dinheiro'
    };
  }

  focusValor(): void {
    if (this.valorInput && this.valorInput.nativeElement) {
        this.valorInput.nativeElement.focus();
        this.valorInput.nativeElement.select();
    }
  }
}
