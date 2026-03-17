import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { ProdutoService, Produto } from '../../services/produto.service';

@Component({
  selector: 'app-produto-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './produto-list.component.html'
})
export class ProdutoListComponent implements OnInit {
  entidadeId: number = 0;
  produtos: Produto[] = [];
  loading = false;
  saving = false;
  showModal = false;
  isEditing = false;
  currentProduto: Produto = this.getEmptyProduto();

  // === Importação CSV ===
  showImportModal = false;
  importMode: 'add' | 'replace' = 'add';
  importPreview: Produto[] = [];
  importFileName = '';
  importError = '';
  importing = false;
  importProgress = 0;

  constructor(
    private authService: AuthService,
    private produtoService: ProdutoService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
        this.entidadeId = ctx.entidadeId;
        this.loadProdutos();
    }
  }

  loadProdutos(): void {
    this.loading = true;
    this.produtoService.getAllByTenant(this.entidadeId).subscribe(data => {
        this.produtos = data;
        this.loading = false;
    });
  }

  getEmptyProduto(): Produto {
      return { descricao: '', sku: '', valorVenda: 0, unidade: 'UN', estoque: 0 };
  }

  openModal(item?: Produto): void {
      this.isEditing = !!item;
      this.currentProduto = item ? { ...item } : this.getEmptyProduto();
      this.showModal = true;
  }

  closeModal(): void {
      this.showModal = false;
  }

  saveItem(): void {
      this.saving = true;
      this.currentProduto.entidadeId = this.entidadeId;
      const req = this.isEditing ? 
          this.produtoService.update(this.currentProduto.id!, this.currentProduto) : 
          this.produtoService.create(this.currentProduto);
      
      req.subscribe({
          next: () => {
              this.closeModal();
              this.loadProdutos();
              this.saving = false;
          },
          error: (err) => {
              console.error('Erro ao salvar produto', err);
              this.saving = false;
          }
      });
  }

  deleteItem(id?: number): void {
      if (!id || !confirm('Tem certeza que deseja excluir este produto?')) return;
      this.produtoService.delete(id).subscribe(() => this.loadProdutos());
  }

  // === Importação CSV ===

  openImportModal(): void {
      this.showImportModal = true;
      this.resetImport();
  }

  closeImportModal(): void {
      this.showImportModal = false;
      this.resetImport();
  }

  resetImport(): void {
      this.importPreview = [];
      this.importFileName = '';
      this.importError = '';
      this.importMode = 'add';
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
      const firstLine = csvText.split('\n')[0];
      const separator = firstLine.includes(';') ? ';' : ',';
      const lines = csvText.split('\n').filter(l => l.trim().length > 0);
      
      if (lines.length < 2) {
          this.importError = 'Arquivo vazio ou sem dados.';
          return;
      }

      const headers = lines[0].split(separator).map(h => h.trim().toUpperCase().replace(/"/g, ''));
      const colMap: Record<string, string> = {
          'PRD_CD_PRODUTO': 'id',
          'PRD_DS_PRODUTO': 'descricao',
          'PRD_VL_PRECO': 'valorVenda',
          'PRD_DS_UNIDADE': 'unidade',
          'PRD_NR_ESTOQUE': 'estoque',
      };

      const indices: Record<string, number> = {};
      for (const [csvCol, field] of Object.entries(colMap)) {
          const idx = headers.indexOf(csvCol);
          if (idx !== -1) indices[field] = idx;
      }

      if (indices['descricao'] === undefined || indices['valorVenda'] === undefined) {
          this.importError = 'O CSV deve conter as colunas PRD_DS_PRODUTO e PRD_VL_PRECO.';
          return;
      }

      const produtos: Produto[] = [];
      for (let i = 1; i < lines.length; i++) {
          const cols = lines[i].split(separator).map(c => c.trim().replace(/"/g, ''));
          if (cols.length < 2) continue;

          const prod: Produto = {
              descricao: cols[indices['descricao']] || '',
              sku: indices['id'] !== undefined ? cols[indices['id']] : '',
              valorVenda: parseFloat(cols[indices['valorVenda']].replace(',', '.')) || 0,
              unidade: indices['unidade'] !== undefined ? cols[indices['unidade']] : 'UN',
              estoque: indices['estoque'] !== undefined ? parseInt(cols[indices['estoque']]) || 0 : 0,
              entidadeId: this.entidadeId
          };

          if (prod.descricao) produtos.push(prod);
      }

      if (produtos.length === 0) {
          this.importError = 'Nenhum produto válido encontrado.';
          return;
      }

      this.importPreview = produtos;
  }

  confirmarImportacao(): void {
      if (this.entidadeId <= 0) {
          alert('Selecione uma entidade primeiro.');
          return;
      }
      if (this.importPreview.length === 0) return;
      
      this.importing = true;
      this.importProgress = 0;

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
                  this.loadProdutos();
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
                  this.importError = 'Erro ao importar produtos.';
                  console.error(err);
              }
          });
      };

      sendNextChunk();
  }
}
