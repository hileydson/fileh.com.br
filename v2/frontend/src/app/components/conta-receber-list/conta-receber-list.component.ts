import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContaReceberService, ContaReceber } from '../../services/conta-receber.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-conta-receber-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './conta-receber-list.component.html',
  styleUrls: ['./conta-receber-list.component.scss']
})
export class ContaReceberListComponent implements OnInit {
  contas: ContaReceber[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentConta: ContaReceber = this.getEmptyConta();

  tenantId: number = 1;

  constructor(
    private contaReceberService: ContaReceberService,
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
    this.loadContas();
  }

  loadContas(): void {
    this.loading = true;
    this.contaReceberService.getAllByTenant(this.tenantId).subscribe({
      next: (data) => {
        this.contas = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar contas a receber', err);
        this.loading = false;
      }
    });
  }

  openModal(conta?: ContaReceber): void {
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
    this.currentConta.usuarioId = this.tenantId;

    if (this.isEditing) {
      this.contaReceberService.update(this.currentConta.id!, this.currentConta).subscribe({
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
      this.contaReceberService.create(this.currentConta).subscribe({
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
    if (confirm('Tem certeza que deseja excluir esta conta a receber?')) {
      this.contaReceberService.delete(id).subscribe({
        next: () => this.loadContas(),
        error: (err) => console.error('Erro ao excluir', err)
      });
    }
  }

  toggleRecebido(conta: ContaReceber): void {
    const updatedConta = { ...conta, recebido: !conta.recebido };
    this.contaReceberService.update(conta.id!, updatedConta).subscribe({
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

  private getEmptyConta(): ContaReceber {
    const today = new Date().toISOString().split('T')[0];
    return {
      descricao: '',
      dataVencimento: today,
      valor: 0,
      numeroParcela: 1,
      fornecedor: '', // Used as Client/Origin in legacy
      tipoConta: 'RECEITA_VENDA',
      recebido: false,
      dataCadastro: today
    };
  }
}
