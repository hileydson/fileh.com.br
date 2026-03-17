import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { ProdutoService, Produto } from '../../services/produto.service';
import { FormaPagamentoService, FormaPagamento } from '../../services/forma-pagamento.service';
import { SituacaoPropostaService, SituacaoProposta } from '../../services/situacao-proposta.service';

@Component({
  selector: 'app-vendas-config',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vendas-config.component.html'
})
export class VendasConfigComponent implements OnInit {
  activeTab: 'situacoes' | 'formas' = 'situacoes';
  
  entidadeId: number = 0;
  loading = false;
  saving = false;
  showModal = false;

  // Situações
  situacoes: SituacaoProposta[] = [];
  currentSituacao: SituacaoProposta = { descricao: '' };

  // Formas de Pagamento
  formas: FormaPagamento[] = [];
  currentForma: FormaPagamento = { descricao: '' };

  isEditing = false;

  constructor(
    private authService: AuthService,
    private formaPagamentoService: FormaPagamentoService,
    private situacaoPropostaService: SituacaoPropostaService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
        this.entidadeId = ctx.entidadeId;
        this.loadData();
    }
  }

  loadData(): void {
    this.loading = true;
    if (this.activeTab === 'situacoes') {
        this.situacaoPropostaService.getAllByTenant(this.entidadeId).subscribe(data => {
            this.situacoes = data as SituacaoProposta[];
            this.loading = false;
        });
    } else if (this.activeTab === 'formas') {
        this.formaPagamentoService.getAllByTenant(this.entidadeId).subscribe(data => {
            this.formas = data as FormaPagamento[];
            this.loading = false;
        });
    }
  }

  switchTab(tab: 'situacoes' | 'formas'): void {
    this.activeTab = tab;
    this.loadData();
  }

  openModal(item?: any): void {
      this.isEditing = !!item;
      if (this.activeTab === 'situacoes') {
          this.currentSituacao = item ? { ...item } : { descricao: '' };
      } else if (this.activeTab === 'formas') {
          this.currentForma = item ? { ...item } : { descricao: '' };
      }
      this.showModal = true;
  }

  closeModal(): void {
      this.showModal = false;
  }

  saveItem(): void {
      this.saving = true;
      if (this.activeTab === 'situacoes') {
          this.currentSituacao.entidadeId = this.entidadeId;
          const req = this.isEditing ? this.situacaoPropostaService.update(this.currentSituacao.id!, this.currentSituacao) : this.situacaoPropostaService.create(this.currentSituacao);
          req.subscribe(() => { this.closeModal(); this.loadData(); this.saving = false; });
      } else if (this.activeTab === 'formas') {
          this.currentForma.entidadeId = this.entidadeId;
          const req = this.isEditing ? this.formaPagamentoService.update(this.currentForma.id!, this.currentForma) : this.formaPagamentoService.create(this.currentForma);
          req.subscribe(() => { this.closeModal(); this.loadData(); this.saving = false; });
      }
  }

  deleteItem(id?: number): void {
      if (!id || !confirm('Tem certeza que deseja excluir?')) return;
      if (this.activeTab === 'situacoes') {
          this.situacaoPropostaService.delete(id).subscribe(() => this.loadData());
      } else if (this.activeTab === 'formas') {
          this.formaPagamentoService.delete(id).subscribe(() => this.loadData());
      }
  }
}
