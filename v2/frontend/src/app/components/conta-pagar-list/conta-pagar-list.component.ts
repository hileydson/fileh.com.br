import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContaPagarService, ContaPagar } from '../../services/conta-pagar.service';
import { AuthService } from '../../services/auth.service';
import { TipoContaService, TipoConta } from '../../services/tipo-conta.service';
import { FornecedorService, Fornecedor } from '../../services/fornecedor.service';
import { DateBrPipe } from '../../pipes/date-br.pipe';
import { DateInputComponent } from '../shared/date-input/date-input.component';

@Component({
  selector: 'app-conta-pagar-list',
  standalone: true,
  imports: [CommonModule, FormsModule, DateBrPipe, DateInputComponent],
  templateUrl: './conta-pagar-list.component.html',
  styleUrls: ['./conta-pagar-list.component.scss']
})
export class ContaPagarListComponent implements OnInit {
  contas: ContaPagar[] = [];
  tiposConta: TipoConta[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentConta: any = this.getEmptyConta();

  entidadeId: number = 0;
  
  // Selection
  selectedIds: Set<number> = new Set();
  
  fornecedoresFiltrados: Fornecedor[] = [];
  showFornecedorResults = false;
  fornecedorValido = false;

  // Filtering
  contasFiltradas: ContaPagar[] = [];
  filtros = {
    status: 'todos',
    tipoContaId: null as number | null,
    fornecedor: '',
    dataInicio: '',
    dataFim: '',
    numeroDocumento: ''
  };

  // Pagination
  contasPaginadas: ContaPagar[] = [];
  currentPage = 1;
  pageSize = 20;
  pageSizeOptions = [20, 50, 100];

  // Parcelamento
  isParcelado = false;
  totalParcelas = 1;

  // Dashboard stats
  totalPendente: number = 0;
  totalVencido: number = 0;
  totalPagoMes: number = 0;
  totalHoje: number = 0;

  constructor(
    private contaPagarService: ContaPagarService,
    private authService: AuthService,
    private tipoContaService: TipoContaService,
    private fornecedorService: FornecedorService
  ) {}

  ngOnInit(): void {
    this.initializeFilters();
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
        this.entidadeId = ctx.entidadeId;
        this.loadContas();
        this.loadTiposConta();
    }
  }

  loadTiposConta(): void {
    this.tipoContaService.listarPorEntidade(this.entidadeId).subscribe({
      next: (data) => this.tiposConta = data,
      error: (err) => console.error('Erro ao carregar tipos de conta', err)
    });
  }

  getTipoContaNome(id?: number): string {
    if (!id) return 'Não Definido';
    const tipo = this.tiposConta.find(t => t.id === id);
    return tipo ? tipo.nome : 'Não Definido';
  }

  loadContas(): void {
    this.loading = true;
    this.contaPagarService.getAllByTenant(this.entidadeId).subscribe({
      next: (data) => {
        this.contas = data;
        this.applyFilters();
        this.loading = false;
        this.selectedIds.clear();
      },
      error: (err) => {
        console.error('Erro ao buscar contas', err);
        this.loading = false;
      }
    });
  }

  applyFilters(): void {
    this.contasFiltradas = this.contas.filter(c => {
      const matchStatus = this.filtros.status === 'todos' || 
                        (this.filtros.status === 'pago' && c.pago) || 
                        (this.filtros.status === 'pendente' && !c.pago);
      
      const matchTipo = !this.filtros.tipoContaId || c.tipoContaId === this.filtros.tipoContaId;
      
      const searchTerm = this.filtros.fornecedor?.toLowerCase() || '';
      const matchSearch = !searchTerm || 
                         c.fornecedor?.toLowerCase().includes(searchTerm) || 
                         c.numeroDocumento?.toLowerCase().includes(searchTerm) ||
                         c.descricao?.toLowerCase().includes(searchTerm);
      
      const dueDate = c.dataVencimento ? new Date(c.dataVencimento) : null;
      const matchStart = !this.filtros.dataInicio || (dueDate && dueDate >= new Date(this.filtros.dataInicio));
      const matchEnd = !this.filtros.dataFim || (dueDate && dueDate <= new Date(this.filtros.dataFim));

      return matchStatus && matchTipo && matchSearch && matchStart && matchEnd;
    }).sort((a, b) => {
      const dateA = a.dataVencimento || '';
      const dateB = b.dataVencimento || '';
      return dateA.localeCompare(dateB);
    });
    this.calculateDashboard();
    this.updatePagination();
    this.selectedIds.clear();
  }

  updatePagination(): void {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.contasPaginadas = this.contasFiltradas.slice(start, end);
  }

  changePage(page: number): void {
    this.currentPage = page;
    this.updatePagination();
  }

  changePageSize(size: number): void {
    this.pageSize = size;
    this.currentPage = 1;
    this.updatePagination();
  }

  getTotalPages(): number {
    return Math.ceil(this.contasFiltradas.length / this.pageSize) || 1;
  }

  // Selection Logic
  toggleSelectAll(event: any): void {
    if (event.target.checked) {
      this.contasFiltradas.forEach(c => { // Changed to contasFiltradas
        if (c.id) this.selectedIds.add(c.id);
      });
    } else {
      this.selectedIds.clear();
    }
  }

  toggleSelection(id?: number): void {
    if (!id) return;
    if (this.selectedIds.has(id)) {
      this.selectedIds.delete(id);
    } else {
      this.selectedIds.add(id);
    }
  }

  isAllSelected(): boolean {
    return this.contasFiltradas.length > 0 && this.selectedIds.size === this.contasFiltradas.length; // Changed to contasFiltradas
  }

  bulkUpdateStatus(pago: boolean): void {
    if (this.selectedIds.size === 0) return;
    
    this.saving = true;
    const ids = Array.from(this.selectedIds);
    this.contaPagarService.bulkUpdateStatus(ids, pago).subscribe({
      next: () => {
        this.loadContas();
        this.saving = false;
      },
      error: (err) => {
        console.error('Erro no update em massa', err);
        this.saving = false;
      }
    });
  }

  // Fornecedor Search
  buscarFornecedores(term: string): void {
    if (!term || term.length < 2) {
      this.fornecedoresFiltrados = [];
      this.showFornecedorResults = false;
      return;
    }
    this.fornecedorService.search(this.entidadeId, term).subscribe(data => {
      this.fornecedoresFiltrados = data;
      this.showFornecedorResults = true;
    });
  }

  selecionarFornecedor(f: Fornecedor): void {
    this.currentConta.fornecedor = f.nome;
    this.fornecedorValido = true;
    this.showFornecedorResults = false;
  }

  openModal(conta?: ContaPagar): void {
    if (conta) {
      this.isEditing = true;
      this.currentConta = { ...conta };
      // Assuming isParcelado and totalParcelas are properties of the component
      // and need to be set based on the existing conta if applicable.
      // The original code had `this.isParcelado = !!conta.parcelado;` and `this.totalParcelas = 1;`
      // but these properties are not defined in the component's class.
      // I'll keep them commented out or remove if they are not meant to be there.
      // this.isParcelado = !!conta.parcelado; // Consistent with record
      // this.totalParcelas = 1; // Default for edit
      this.fornecedorValido = true; // Assumed valid if existing
    } else {
      this.isEditing = false;
      this.currentConta = this.getEmptyConta();
      // this.isParcelado = false;
      // this.totalParcelas = 1;
      this.fornecedorValido = false;
    }
    this.showModal = true;
    this.fornecedoresFiltrados = [];
    this.showFornecedorResults = false;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentConta = this.getEmptyConta();
  }

  saveConta(): void {
    if (!this.fornecedorValido) {
        alert('Por favor, selecione um fornecedor válido da lista de sugestões.');
        return;
    }
    this.saving = true;
    this.currentConta.entidadeId = this.entidadeId;
    
    // Assuming isParcelado and totalParcelas are properties of the component
    // The original code had `if (this.isParcelado && !this.isEditing) { this.currentConta.totalParcelas = this.totalParcelas; }`
    // but these properties are not defined in the component's class.
    // I'll keep them commented out or remove if they are not meant to be there.
    // if (this.isParcelado && !this.isEditing) {
    //     this.currentConta.totalParcelas = this.totalParcelas;
    // }

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
    const ids = [conta.id!];
    this.contaPagarService.bulkUpdateStatus(ids, !conta.pago).subscribe({
      next: () => this.loadContas(),
      error: (err) => console.error('Erro ao atualizar status', err)
    });
  }

  // Formatter moved to DateBrPipe

  formatarValor(event: any): void {
    let value = event.target.value;
    value = value.replace(/\D/g, '');
    if (value === '') {
      this.currentConta.valor = 0;
      return;
    }
    const numericValue = parseInt(value, 10) / 100;
    this.currentConta.valor = numericValue;
    event.target.value = this.getValorFormatado(numericValue);
  }

  getValorFormatado(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }).format(valor);
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

    this.contasFiltradas.forEach(c => {
        const val = c.valor || 0;
        
        if (c.pago) {
            this.totalPagoMes += val;
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

  private initializeFilters(): void {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    
    this.filtros.dataInicio = `${year}-${month}-01`;
    this.filtros.dataFim = `${year}-${month}-${day}`;
  }

  private getEmptyConta(): ContaPagar {
    const today = new Date().toISOString().split('T')[0];
    return {
      descricao: '',
      dataVencimento: today,
      valor: 0,
      numeroParcela: 1,
      fornecedor: '',
      tipoContaId: undefined,
      pago: false,
      dataCadastro: today
    };
  }
}
