import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContaPagarService, ContaPagar } from '../../services/conta-pagar.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-conta-pagar-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './conta-pagar-list.component.html',
  styleUrls: ['./conta-pagar-list.component.scss']
})
export class ContaPagarListComponent implements OnInit {
  contas: ContaPagar[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentConta: ContaPagar = this.getEmptyConta();

  entidadeId: number = 0;

  // Dashboard stats
  totalPendente: number = 0;
  totalVencido: number = 0;
  totalPagoMes: number = 0;
  totalHoje: number = 0;

  constructor(
    private contaPagarService: ContaPagarService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
        this.entidadeId = ctx.entidadeId;
        this.loadContas();
    }
  }

  loadContas(): void {
    this.loading = true;
    this.contaPagarService.getAllByTenant(this.entidadeId).subscribe({
      next: (data) => {
        this.contas = data;
        this.calculateDashboard();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar contas', err);
        this.loading = false;
      }
    });
  }

  openModal(conta?: ContaPagar): void {
    if (conta) {
      this.isEditing = true;
      this.currentConta = { ...conta };
    } else {
      this.isEditing = false;
      this.currentConta = this.getEmptyConta();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentConta = this.getEmptyConta();
  }

  saveConta(): void {
    this.saving = true;
    this.currentConta.entidadeId = this.entidadeId;

    if (this.isEditing) {
      this.contaPagarService.update(this.currentConta.id!, this.currentConta).subscribe({
        next: () => {
          this.loadContas();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao atualizar', err);
          this.saving = false;
        }
      });
    } else {
      this.contaPagarService.create(this.currentConta).subscribe({
        next: () => {
          this.loadContas();
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

  deleteConta(id?: number): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir esta conta?')) {
      this.contaPagarService.delete(id).subscribe({
        next: () => this.loadContas(),
        error: (err) => console.error('Erro ao excluir', err)
      });
    }
  }

  togglePago(conta: ContaPagar): void {
    const updatedConta = { ...conta, pago: !conta.pago };
    this.contaPagarService.update(conta.id!, updatedConta).subscribe({
      next: () => this.loadContas(),
      error: (err) => console.error('Erro ao atualizar status', err)
    });
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
    return 0; // Not used here, just parity
  }

  calculateDashboard(): void {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const todayStr = new Date().toISOString().split('T')[0];
    
    // reset
    this.totalPendente = 0;
    this.totalVencido = 0;
    this.totalPagoMes = 0;
    this.totalHoje = 0;

    const currentMonth = today.getMonth();
    const currentYear = today.getFullYear();

    this.contas.forEach(c => {
        const val = c.valor || 0;
        
        if (c.pago) {
            // Check if paid this month (approx based on dueDate since we don't have paymentDate)
            const dueD = new Date(c.dataVencimento);
            if (dueD.getMonth() === currentMonth && dueD.getFullYear() === currentYear) {
               this.totalPagoMes += val;
            }
        } else {
            this.totalPendente += val;
            
            if (c.dataVencimento < todayStr) {
                this.totalVencido += val;
            }
            if (c.dataVencimento === todayStr) {
                this.totalHoje += val;
            }
        }
    });
  }

  private getEmptyConta(): ContaPagar {
    const today = new Date().toISOString().split('T')[0];
    return {
      descricao: '',
      dataVencimento: today,
      valor: 0,
      numeroParcela: 1,
      fornecedor: '',
      tipoConta: 'DESPESA_FIXA',
      pago: false,
      dataCadastro: today
    };
  }
}
