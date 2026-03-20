import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PropostaComercialService, PropostaComercial } from '../../services/proposta-comercial.service';
import { ItemPropostaService, ItemProposta } from '../../services/item-proposta.service';
import { ProdutoService, Produto } from '../../services/produto.service';
import { ClienteService, Cliente } from '../../services/cliente.service';
import { SituacaoPropostaService, SituacaoProposta } from '../../services/situacao-proposta.service';
import { FormaPagamentoService, FormaPagamento } from '../../services/forma-pagamento.service';
import { AuthService } from '../../services/auth.service';
import { Observable, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

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

  entidadeId: number = 0;

  // Items and Products
  activeTab: 'dados' | 'itens' = 'dados';
  produtos: Produto[] = [];
  itens: ItemProposta[] = [];
  deletedItens: number[] = [];
  
  // New Item Form
  novoItemProdutoId: number | null = null;
  novoItemQuantidade: number = 1;

  // Filters and Pagination
  filtros = {
    id: '',
    cliente: '',
    dataPrevistaInicio: '',
    dataPrevistaFim: '',
    situacao: ''
  };
  currentPage = 1;
  pageSize = 20;
  clientes: Cliente[] = [];
  situacoes: SituacaoProposta[] = [];
  formasPagamento: FormaPagamento[] = [];
  clienteMap: { [key: number]: string } = {};

  constructor(
    private propostaService: PropostaComercialService,
    private itemService: ItemPropostaService,
    private produtoService: ProdutoService,
    private clienteService: ClienteService,
    private situacaoService: SituacaoPropostaService,
    private formaPagamentoService: FormaPagamentoService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
        this.entidadeId = ctx.entidadeId;
        this.loadPropostas();
    }
  }

  loadPropostas(): void {
    this.loading = true;
    this.propostaService.getAllByTenant(this.entidadeId).subscribe({
      next: (data) => {
        this.propostas = data;
        this.loading = false;
        // Preload children
        this.produtoService.getAllByTenant(this.entidadeId).subscribe(prods => this.produtos = prods);
        this.clienteService.getAllByTenant(this.entidadeId).subscribe(clis => {
          this.clientes = clis;
          this.clienteMap = {};
          clis.forEach(c => { if(c.id) this.clienteMap[c.id] = c.nome; });
        });
        
        // Load situations and payment methods
        this.situacaoService.getAllByTenant(this.entidadeId).subscribe(data => this.situacoes = data);
        this.formaPagamentoService.getAllByTenant(this.entidadeId).subscribe(data => this.formasPagamento = data);
      },
      error: (err) => {
        console.error('Erro ao buscar propostas', err);
        this.loading = false;
      }
    });
  }

  openModal(proposta?: PropostaComercial): void {
    this.activeTab = 'dados';
    this.itens = [];
    this.deletedItens = [];
    this.novoItemProdutoId = null;
    this.novoItemQuantidade = 1;

    if (proposta) {
      this.isEditing = true;
      this.currentProposta = { ...proposta };
      this.itemService.getAllByProposta(proposta.id!).subscribe(res => {
          this.itens = res;
      });
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

  // --- Itens Logic ---
  
  adicionarItem(): void {
    if (!this.novoItemProdutoId) return;
    const prod = this.produtos.find(p => p.id == this.novoItemProdutoId);
    if (!prod) return;

    this.itens.push({
        propostaId: this.currentProposta.id || 0,
        descricao: prod.descricao,
        valor: prod.valorVenda,
        quantidade: this.novoItemQuantidade,
        valorDesconto: 0,
        unidade: prod.unidade || 'UN'
    });

    this.novoItemProdutoId = null;
    this.novoItemQuantidade = 1;
    this.calcularTotais();
  }

  removerItem(index: number): void {
    const item = this.itens[index];
    if (item.id) {
       this.deletedItens.push(item.id);
    }
    this.itens.splice(index, 1);
    this.calcularTotais();
  }

  calcularTotais(): void {
     let totalItens = 0;
     for (let it of this.itens) {
         totalItens += ((it.valor * it.quantidade) - (it.valorDesconto || 0));
     }
     const frete = this.currentProposta.valorFrete || 0;
     const desc = this.currentProposta.valorDesconto || 0;
     this.currentProposta.valorTotal = totalItens + frete - desc;
  }

  // --- Save Logic ---

  saveProposta(): void {
    this.saving = true;
    this.currentProposta.entidadeId = this.entidadeId;

    if (this.isEditing) {
      this.propostaService.update(this.currentProposta.id!, this.currentProposta).subscribe({
        next: (savedProposta) => {
          this.saveItemsEConcluir(savedProposta.id!);
        },
        error: (err) => {
          console.error('Erro ao atualizar', err);
          this.saving = false;
        }
      });
    } else {
      this.propostaService.create(this.currentProposta).subscribe({
        next: (savedProposta) => {
          this.saveItemsEConcluir(savedProposta.id!);
        },
        error: (err) => {
          console.error('Erro ao criar', err);
          this.saving = false;
        }
      });
    }
  }

  private saveItemsEConcluir(propostaId: number): void {
     const operations: Observable<any>[] = [];
     
     // 1. Deletions
     for (let delId of this.deletedItens) {
         operations.push(this.itemService.delete(delId).pipe(catchError(() => of(null))));
     }

     // 2. Inserts / Updates
     for (let item of this.itens) {
         item.propostaId = propostaId; // crucial for new proposals
         if (item.id) {
             operations.push(this.itemService.update(item.id, item).pipe(catchError(() => of(null))));
         } else {
             operations.push(this.itemService.create(item).pipe(catchError(() => of(null))));
         }
     }

     if (operations.length === 0) {
         this.loadPropostas();
         this.closeModal();
         this.saving = false;
         return;
     }

     forkJoin(operations).subscribe(() => {
         this.loadPropostas();
         this.closeModal();
         this.saving = false;
     });
  }

  // --- Filter & Pagination Getters ---

  get propostasFiltradas(): PropostaComercial[] {
    return this.propostas.filter(p => {
      const matchId = !this.filtros.id || p.id?.toString().includes(this.filtros.id);
      
      const cliNome = p.clienteId ? (this.clienteMap[p.clienteId] || '').toLowerCase() : '';
      const matchCliente = !this.filtros.cliente || 
                          cliNome.includes(this.filtros.cliente.toLowerCase()) || 
                          p.clienteId?.toString() === this.filtros.cliente;

      const matchSituacao = !this.filtros.situacao || p.situacao === this.filtros.situacao;

      // Dates
      const matchDataPrev = this.matchDateRange(p.dataPrevista, this.filtros.dataPrevistaInicio, this.filtros.dataPrevistaFim);

      return matchId && matchCliente && matchSituacao && matchDataPrev;
    });
  }

  private matchDateRange(dateStr: string | undefined, start: string, end: string): boolean {
    if (!start && !end) return true;
    if (!dateStr) return false;
    
    // dateStr is YYYY-MM-DD
    if (start && dateStr < start) return false;
    if (end && dateStr > end) return false;
    return true;
  }

  get totalPaginas(): number {
    return Math.ceil(this.propostasFiltradas.length / this.pageSize);
  }

  get propostasExibidas(): PropostaComercial[] {
    const inicio = (this.currentPage - 1) * this.pageSize;
    return this.propostasFiltradas.slice(inicio, inicio + this.pageSize);
  }

  onFilterChange(): void {
    this.currentPage = 1;
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
      valorTotal: 0,
      situacao: 'EM COTAÇÃO', // Defaulting based on assumed workflow
      dataCadastro: today
    };
  }
}
