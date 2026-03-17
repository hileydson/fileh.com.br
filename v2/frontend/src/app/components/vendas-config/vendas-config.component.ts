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
  activeTab: 'produtos' | 'situacoes' | 'formas' = 'produtos';
  
  entidadeId: number = 0;
  loading = false;
  saving = false;
  showModal = false;

  // Produtos
  produtos: Produto[] = [];
  currentProduto: Produto = { descricao: '', sku: '', valorVenda: 0, unidade: 'UN', estoque: 0 };

  // Situações
  situacoes: SituacaoProposta[] = [];
  currentSituacao: SituacaoProposta = { descricao: '' };

  // Formas de Pagamento
  formas: FormaPagamento[] = [];
  currentForma: FormaPagamento = { descricao: '' };

  isEditing = false;

  // === Importação CSV ===
  showImportModal = false;
  importMode: 'add' | 'replace' = 'add';
  importPreview: Produto[] = [];
  importFileName = '';
  importError = '';
  importing = false;
  importProgress = 0; // Porcentagem de 0 a 100

  constructor(
    private authService: AuthService,
    private produtoService: ProdutoService,
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
    if (this.activeTab === 'produtos') {
        this.produtoService.getAllByTenant(this.entidadeId).subscribe(data => {
            this.produtos = data;
            this.loading = false;
        });
    } else if (this.activeTab === 'situacoes') {
        this.situacaoPropostaService.getAllByTenant(this.entidadeId).subscribe(data => {
            this.situacoes = data;
            this.loading = false;
        });
    } else if (this.activeTab === 'formas') {
        this.formaPagamentoService.getAllByTenant(this.entidadeId).subscribe(data => {
            this.formas = data;
            this.loading = false;
        });
    }
  }

  switchTab(tab: 'produtos' | 'situacoes' | 'formas'): void {
    this.activeTab = tab;
    this.loadData();
  }

  openModal(item?: any): void {
      this.isEditing = !!item;
      if (this.activeTab === 'produtos') {
          this.currentProduto = item ? { ...item } : { descricao: '', sku: '', valorVenda: 0, unidade: 'UN', estoque: 0 };
      } else if (this.activeTab === 'situacoes') {
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
      if (this.activeTab === 'produtos') {
          this.currentProduto.entidadeId = this.entidadeId;
          const req = this.isEditing ? this.produtoService.update(this.currentProduto.id!, this.currentProduto) : this.produtoService.create(this.currentProduto);
          req.subscribe(() => { this.closeModal(); this.loadData(); this.saving = false; });
      } else if (this.activeTab === 'situacoes') {
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
      if (this.activeTab === 'produtos') {
          this.produtoService.delete(id).subscribe(() => this.loadData());
      } else if (this.activeTab === 'situacoes') {
          this.situacaoPropostaService.delete(id).subscribe(() => this.loadData());
      } else if (this.activeTab === 'formas') {
          this.formaPagamentoService.delete(id).subscribe(() => this.loadData());
      }
  }

  // === Importação CSV ===

  openImportModal(): void {
      this.showImportModal = true;
      this.importPreview = [];
      this.importFileName = '';
      this.importError = '';
      this.importMode = 'add';
      this.importProgress = 0;
  }

  closeImportModal(): void {
      this.showImportModal = false;
      this.importPreview = [];
      this.importFileName = '';
      this.importError = '';
      this.importProgress = 0;
  }

  onFileSelected(event: Event): void {
      const input = event.target as HTMLInputElement;
      if (!input.files || input.files.length === 0) return;

      const file = input.files[0];
      this.importFileName = file.name;
      this.importError = '';
      this.importPreview = [];

      const reader = new FileReader();
      reader.onload = (e) => {
          try {
              const text = e.target?.result as string;
              this.parseCSV(text);
          } catch (err) {
              this.importError = 'Erro ao ler o arquivo. Verifique se é um CSV válido.';
          }
      };
      reader.readAsText(file, 'UTF-8');
  }

  private parseCSV(csvText: string): void {
      // Detectar separador (; ou ,)
      const firstLine = csvText.split('\n')[0];
      const separator = firstLine.includes(';') ? ';' : ',';

      const lines = csvText.split('\n').filter(l => l.trim().length > 0);
      if (lines.length < 2) {
          this.importError = 'Arquivo vazio ou sem dados (precisa ter cabeçalho + linhas).';
          return;
      }

      // Ler cabeçalho e normalizar
      const headers = lines[0].split(separator).map(h => h.trim().toUpperCase().replace(/"/g, ''));

      // Mapeamento do CSV legado para o modelo interno
      const colMap: Record<string, string> = {
          'PRD_CD_PRODUTO': 'id',
          'PRD_DS_PRODUTO': 'descricao',
          'PRD_VL_PRECO': 'valorVenda',
          'PRD_DS_UNIDADE': 'unidade',
          'PRD_NR_ESTOQUE': 'estoque',
      };

      // Descobrir índices das colunas
      const indices: Record<string, number> = {};
      for (const [csvCol, field] of Object.entries(colMap)) {
          const idx = headers.indexOf(csvCol);
          if (idx !== -1) {
              indices[field] = idx;
          }
      }

      // Validar que pelo menos descricao e valorVenda existem
      if (indices['descricao'] === undefined || indices['valorVenda'] === undefined) {
          this.importError = 'O CSV deve conter pelo menos as colunas PRD_DS_PRODUTO e PRD_VL_PRECO.';
          return;
      }

      const produtos: Produto[] = [];
      for (let i = 1; i < lines.length; i++) {
          const cols = lines[i].split(separator).map(c => c.trim().replace(/"/g, ''));
          if (cols.length < 2) continue;

          const prod: Produto = {
              descricao: indices['descricao'] !== undefined ? cols[indices['descricao']] : '',
              sku: indices['id'] !== undefined ? cols[indices['id']] : '', // Usa o código legado como SKU
              valorVenda: indices['valorVenda'] !== undefined ? parseFloat(cols[indices['valorVenda']].replace(',', '.')) || 0 : 0,
              unidade: indices['unidade'] !== undefined ? cols[indices['unidade']] : 'UN',
              estoque: indices['estoque'] !== undefined ? parseInt(cols[indices['estoque']]) || 0 : 0,
              entidadeId: this.entidadeId
          };

          if (prod.descricao) {
              produtos.push(prod);
          }
      }

      if (produtos.length === 0) {
          this.importError = 'Nenhum produto válido encontrado no arquivo.';
          return;
      }

      this.importPreview = produtos;
  }

  confirmarImportacao(): void {
      if (this.entidadeId <= 0) {
          alert('Por favor, selecione uma entidade antes de importar.');
          return;
      }
      if (this.importPreview.length === 0) return;
      
      this.importing = true;
      this.importProgress = 0;
      this.importError = '';

      if (this.importMode === 'replace') {
          this.produtoService.deleteAll(this.entidadeId).subscribe({
              next: () => this.processInChunks(this.importPreview),
              error: () => {
                  this.importing = false;
                  this.importError = 'Erro ao limpar produtos existentes.';
              }
          });
      } else {
          this.processInChunks(this.importPreview);
      }
  }

  private processInChunks(allProducts: Produto[]): void {
      const CHUNK_SIZE = 500;
      const totalItems = allProducts.length;
      let startIndex = 0;

      const sendNextChunk = () => {
          if (startIndex >= totalItems) {
              this.importing = false;
              this.importProgress = 100;
              setTimeout(() => {
                  this.closeImportModal();
                  this.loadData();
              }, 1000);
              return;
          }

          const chunk = allProducts.slice(startIndex, startIndex + CHUNK_SIZE);
          this.produtoService.importAdd(this.entidadeId, chunk).subscribe({
              next: () => {
                  startIndex += CHUNK_SIZE;
                  this.importProgress = Math.min(Math.round((startIndex / totalItems) * 100), 100);
                  sendNextChunk();
              },
              error: (err) => {
                  this.importing = false;
                  this.importError = `Erro ao importar lote (a partir de ${startIndex}).`;
                  console.error('Import error', err);
              }
          });
      };

      sendNextChunk();
  }
}
