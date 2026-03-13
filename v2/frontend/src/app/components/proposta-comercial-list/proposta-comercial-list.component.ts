import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PropostaComercialService, PropostaComercial } from '../../services/proposta-comercial.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-proposta-comercial-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './proposta-comercial-list.component.html',
  styleUrls: ['./proposta-comercial-list.component.scss']
})
export class PropostaComercialListComponent implements OnInit {
  propostas: PropostaComercial[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentProposta: PropostaComercial = this.getEmptyProposta();

  tenantId: number = 1;

  constructor(
    private propostaService: PropostaComercialService,
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
    this.loadPropostas();
  }

  loadPropostas(): void {
    this.loading = true;
    this.propostaService.getAllByTenant(this.tenantId).subscribe({
      next: (data) => {
        this.propostas = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar propostas', err);
        this.loading = false;
      }
    });
  }

  openModal(proposta?: PropostaComercial): void {
    if (proposta) {
      this.isEditing = true;
      this.currentProposta = { ...proposta };
    } else {
      this.isEditing = false;
      this.currentProposta = this.getEmptyProposta();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentProposta = this.getEmptyProposta();
  }

  saveProposta(): void {
    this.saving = true;
    this.currentProposta.usuarioId = this.tenantId;

    if (this.isEditing) {
      this.propostaService.update(this.currentProposta.id!, this.currentProposta).subscribe({
        next: () => {
          this.loadPropostas();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao atualizar', err);
          this.saving = false;
        }
      });
    } else {
      this.propostaService.create(this.currentProposta).subscribe({
        next: () => {
          this.loadPropostas();
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

  deleteProposta(id?: number): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir esta proposta comercial?')) {
      this.propostaService.delete(id).subscribe({
        next: () => this.loadPropostas(),
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

  private getEmptyProposta(): PropostaComercial {
    const today = new Date().toISOString().split('T')[0];
    return {
      valorDesconto: 0,
      valorFrete: 0,
      situacao: 'EM COTAÇÃO', // Defaulting based on assumed workflow
      dataCadastro: today
    };
  }
}
