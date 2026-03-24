import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
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
  @ViewChild('qtdInput') qtdInput!: ElementRef;
  
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
  produtoBusca = '';
  produtosFiltrados: Produto[] = [];
  showProdutoResults = false;
  produtoValido = false;

  // Busca de Clientes
  clienteBusca: string = '';
  clientesFiltrados: Cliente[] = [];
  showClienteResults = false;
  clienteValido = false;
  clienteSelecionado: Cliente | null = null;

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
  ) {
    const ctx = this.authService.getAuthContext();
    this.entidadeId = ctx?.entidadeId || 0;
  }

  ngOnInit(): void {
    if (this.entidadeId !== undefined) {
        this.loadPropostas();
    }
  }

  loadPropostas(): void {
    this.loading = true;
    this.propostaService.getAllByTenant(this.entidadeId).subscribe({
      next: (data) => {
        // Sort by dataCadastro (desc) and then id (desc)
        this.propostas = data.sort((a, b) => {
          const dateA = a.dataCadastro || '';
          const dateB = b.dataCadastro || '';
          if (dateA !== dateB) return dateB.localeCompare(dateA);
          return (b.id || 0) - (a.id || 0);
        });
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
      // Set client search state
      if (this.currentProposta.clienteId) {
        this.clienteValido = true;
        const cli = this.clientes.find(c => c.id === this.currentProposta.clienteId);
        if (cli) {
          this.clienteBusca = cli.nome;
          this.clienteSelecionado = cli;
        }
      }
    } else {
      this.isEditing = false;
      this.currentProposta = this.getEmptyProposta();
      const ctx = this.authService.getAuthContext();
      if (ctx) {
          this.currentProposta.atendente = ctx.nome;
      }
    }
    this.showModal = true;
    this.produtoBusca = '';
    this.produtosFiltrados = [];
    this.showProdutoResults = false;
    this.produtoValido = false;
    
    // Reset client search logic for new/switching
    if (!proposta) {
      this.clienteBusca = '';
      this.clienteValido = false;
      this.clienteSelecionado = null;
    }
  }

  closeModal(): void {
    this.showModal = false;
    this.currentProposta = this.getEmptyProposta();
  }

  // --- Itens Logic ---
  
  buscarProdutos(term: string): void {
    if (!term || term.trim().length < 2) {
      this.produtosFiltrados = [];
      this.showProdutoResults = false;
      return;
    }
    this.produtoService.search(this.entidadeId, term).subscribe(data => {
      this.produtosFiltrados = data;
      this.showProdutoResults = true;
    });
  }

  selecionarProduto(p: Produto): void {
    this.produtoBusca = p.descricao;
    this.novoItemProdutoId = p.id!;
    this.produtoValido = true;
    this.showProdutoResults = false;
    
    // Focus and select quantity input
    setTimeout(() => {
      // Multiple attempts to ensure it works
      const el = document.querySelector('input[name="novoItemQtd"]') as HTMLInputElement;
      if (el) {
        el.focus();
        el.select();
      }
    }, 50);

    setTimeout(() => {
      const el = document.querySelector('input[name="novoItemQtd"]') as HTMLInputElement;
      if (el) {
        el.focus();
        el.select();
      }
    }, 150);
  }

  buscarClientes(term: string): void {
    if (!term || term.trim().length < 2) {
      this.clientesFiltrados = [];
      this.showClienteResults = false;
      return;
    }
    this.clienteService.search(this.entidadeId, term).subscribe(data => {
      this.clientesFiltrados = data;
      this.showClienteResults = true;
    });
  }

  selecionarCliente(c: Cliente): void {
    this.currentProposta.clienteId = c.id;
    this.clienteBusca = c.nome;
    this.clienteSelecionado = c;
    this.clienteValido = true;
    this.showClienteResults = false;
  }

  adicionarItem(): void {
    if (!this.produtoValido || !this.novoItemProdutoId) {
      alert('Por favor, selecione um produto válido da lista de sugestões.');
      return;
    }
    const prod = this.produtosFiltrados.find(p => p.id == this.novoItemProdutoId);
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
    this.produtoBusca = '';
    this.produtoValido = false;
    this.calcularTotais();
    
    // After adding, focus back to product search
    setTimeout(() => {
      const el = document.querySelector('input[name="prodBusca"]') as HTMLInputElement;
      if (el) el.focus();
    }, 100);
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
     let subtotal = 0;
     for (let it of this.itens) {
         subtotal += (it.valor * it.quantidade);
     }
     this.currentProposta.valorTotal = subtotal - (this.currentProposta.valorDesconto || 0);
  }

  formatarDesconto(event: any): void {
    let value = event.target.value;
    value = value.replace(/\D/g, '');
    if (value === '') {
      this.currentProposta.valorDesconto = 0;
    } else {
      const numericValue = parseInt(value, 10) / 100;
      this.currentProposta.valorDesconto = numericValue;
    }
    event.target.value = this.getValorFormatado(this.currentProposta.valorDesconto || 0);
    this.calcularTotais();
  }

  formatarValorItem(event: any, index: number): void {
    let value = event.target.value;
    value = value.replace(/\D/g, '');
    if (value === '') {
      this.itens[index].valor = 0;
      return;
    }
    const numericValue = parseInt(value, 10) / 100;
    this.itens[index].valor = numericValue;
    event.target.value = this.getValorFormatado(numericValue);
    this.calcularTotais();
  }

  selectVal(event: any): void {
    if (event.target && event.target.select) {
      event.target.select();
    }
  }

  getValorFormatado(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(valor);
  }

  // --- Save Logic ---

  saveProposta(): void {
    this.saving = true;
    this.currentProposta.entidadeId = this.entidadeId;
    this.currentProposta.usuarioId = this.authService.getAuthContext()?.id;

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

   imprimirProposta(p: PropostaComercial): void {
     // Pre-load items before printing
     this.itemService.getAllByProposta(p.id!).subscribe(items => {
        const cliId = p.clienteId;
        const cliObj = this.clientes.find(c => c.id === cliId);
        const authCtx = this.authService.getAuthContext();
        const footerMsg = authCtx?.msgFooter || '';
        
        const printWindow = window.open('', '_blank');
        if (!printWindow) return;

        const html = `
          <html>
            <head>
              <title>Proposta Comercial - ${p.id}</title>
              <style>
                body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; padding: 40px; color: #334155; line-height: 1.5; }
                .header { display: flex; justify-content: space-between; align-items: flex-start; border-bottom: 2px solid #e2e8f0; padding-bottom: 20px; margin-bottom: 30px; }
                .title { font-size: 28px; font-weight: 800; color: #1e40af; }
                .date-header { text-align: right; font-size: 14px; color: #64748b; font-weight: 500; }
                
                .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 30px; }
                .info-box { border: 1px solid #e2e8f0; padding: 20px; border-radius: 12px; background: #f8fafc; }
                .label { font-size: 11px; text-transform: uppercase; color: #64748b; font-weight: 700; margin-bottom: 8px; letter-spacing: 0.05em; }
                .value { font-size: 14px; font-weight: 600; color: #1e293b; }
                
                .client-details { font-size: 13px; color: #475569; margin-top: 5px; line-height: 1.4; }
                
                table { width: 100%; border-collapse: collapse; margin-bottom: 40px; border-radius: 8px; overflow: hidden; }
                th { text-align: left; padding: 14px; background: #1e40af; color: white; font-size: 12px; font-weight: 600; text-transform: uppercase; }
                td { padding: 14px; border-bottom: 1px solid #f1f5f9; font-size: 13px; vertical-align: middle; }
                tr:nth-child(even) { background-color: #f8fafc; }
                
                .total-section { float: right; width: 300px; background: #f1f5f9; padding: 20px; border-radius: 12px; }
                .total-row { display: flex; justify-content: space-between; padding: 6px 0; font-size: 14px; }
                .total-final { font-size: 20px; font-weight: 800; color: #1e40af; border-top: 2px solid #cbd5e1; margin-top: 12px; padding-top: 12px; }
                
                .footer { position: fixed; bottom: 40px; left: 40px; right: 40px; text-align: center; border-top: 1px solid #e2e8f0; padding-top: 20px; font-size: 12px; color: #94a3b8; font-style: italic; }
                
                @media print {
                  body { padding: 0; }
                  .footer { position: absolute; }
                }
              </style>
            </head>
            <body>
              <div class="header">
                <div class="title">PROPOSTA COMERCIAL - ${p.id}</div>
                <div class="date-header">
                  <div>Data de Emissão: ${this.formatDateBR(p.dataCadastro)}</div>
                </div>
              </div>

              <div class="info-grid">
                <div class="info-box">
                  <div class="label">Dados do Cliente</div>
                  <div class="value">${cliObj?.nome || 'N/A'}</div>
                  <div class="client-details">
                    ${cliObj?.logradouro ? `<div><strong>Endereço:</strong> ${cliObj.logradouro}, ${cliObj.bairro} - ${cliObj.cidade}/${cliObj.uf}</div>` : ''}
                    ${cliObj?.telefone ? `<div><strong>Telefone:</strong> ${cliObj.telefone}</div>` : ''}
                    ${cliObj?.email ? `<div><strong>E-mail:</strong> ${cliObj.email}</div>` : ''}
                  </div>
                </div>
                <div class="info-box">
                  <div class="label">Condições de Venda</div>
                  <div class="value" style="margin-bottom: 10px;">
                    <span style="color: #64748b; font-weight: 500;">Atendente:</span> ${p.atendente || 'N/A'}
                  </div>
                  <div class="value" style="margin-bottom: 10px;">
                    <span style="color: #64748b; font-weight: 500;">Forma de Pgto:</span> ${p.formaPagamento || 'A combinar'}
                  </div>
                  <div class="value">
                    <span style="color: #64748b; font-weight: 500;">Situação:</span> ${p.situacao}
                  </div>
                </div>
              </div>

              <table>
                <thead>
                  <tr>
                    <th>Item / Descrição</th>
                    <th style="text-align: center">Qtd</th>
                    <th style="text-align: center">Un</th>
                    <th style="text-align: right">Vlr Unitário</th>
                    <th style="text-align: right">Subtotal</th>
                  </tr>
                </thead>
                <tbody>
                  ${items.map(it => `
                    <tr>
                      <td><strong>${it.descricao}</strong></td>
                      <td style="text-align: center">${it.quantidade}</td>
                      <td style="text-align: center">${it.unidade}</td>
                      <td style="text-align: right">R$ ${this.getValorFormatado(it.valor)}</td>
                      <td style="text-align: right; font-weight: 600;">R$ ${this.getValorFormatado(it.valor * it.quantidade)}</td>
                    </tr>
                  `).join('')}
                </tbody>
              </table>

              <div style="width: 100%; overflow: hidden;">
                <div class="total-section">
                  <div class="total-row">
                    <span>Subtotal:</span>
                    <span>R$ ${this.getValorFormatado(p.valorTotal + (p.valorDesconto || 0))}</span>
                  </div>
                  <div class="total-row" style="color: #dc2626;">
                    <span>Desconto:</span>
                    <span>- R$ ${this.getValorFormatado(p.valorDesconto || 0)}</span>
                  </div>
                  <div class="total-row total-final">
                    <span>TOTAL:</span>
                    <span>R$ ${this.getValorFormatado(p.valorTotal)}</span>
                  </div>
                </div>
              </div>

              ${p.observacao ? `
                <div style="margin-top: 40px; padding: 20px; border: 1px dashed #cbd5e1; border-radius: 8px;">
                  <div class="label">Observações</div>
                  <div style="font-size: 13px;">${p.observacao.replace(/\n/g, '<br>')}</div>
                </div>
              ` : ''}

              <div class="footer">
                ${footerMsg}
              </div>

              <script>
                window.onload = () => {
                  setTimeout(() => {
                    window.print();
                    // window.close(); // Opcional
                  }, 500);
                };
              </script>
            </body>
          </html>
        `;

        printWindow.document.write(html);
        printWindow.document.close();
     });
  }
}
