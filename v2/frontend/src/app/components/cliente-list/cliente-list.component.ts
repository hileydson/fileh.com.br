import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService, Cliente } from '../../services/cliente.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-cliente-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cliente-list.component.html',
  styleUrls: ['./cliente-list.component.scss']
})
export class ClienteListComponent implements OnInit {
  clientes: Cliente[] = [];
  loading = true;
  saving = false;
  
  showModal = false;
  isEditing = false;
  currentCliente: Cliente = this.getEmptyCliente();

  entidadeId: number = 0;

  // Filtros e Paginação
  filtros = {
    idNome: '',
    cpf: '',
    bairro: ''
  };
  currentPage = 1;
  pageSize = 20;

  listaUfs: string[] = [
    'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 
    'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 
    'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO'
  ];

  constructor(
    private clienteService: ClienteService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx && ctx.entidadeId) {
      this.entidadeId = ctx.entidadeId;
      this.loadClientes();
    }
  }

  loadClientes(): void {
    this.loading = true;
    // Calling with 0 as it's now global in backend
    this.clienteService.getAllByTenant(0).subscribe({
      next: (data) => {
        this.clientes = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar clientes', err);
        this.loading = false;
      }
    });
  }

  openModal(cliente?: Cliente): void {
    if (cliente) {
      this.isEditing = true;
      this.currentCliente = { ...cliente };
    } else {
      this.isEditing = false;
      this.currentCliente = this.getEmptyCliente();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentCliente = this.getEmptyCliente();
  }

  saveCliente(): void {
    this.saving = true;
    this.currentCliente.entidadeId = this.entidadeId; // Ensure entity ID is set

    if (this.isEditing) {
      this.clienteService.update(this.currentCliente.id!, this.currentCliente).subscribe({
        next: () => {
          this.loadClientes();
          this.closeModal();
          this.saving = false;
        },
        error: (err) => {
          console.error('Erro ao atualizar', err);
          this.saving = false;
        }
      });
    } else {
      this.clienteService.create(this.currentCliente).subscribe({
        next: () => {
          this.loadClientes();
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

  deleteCliente(id?: number): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir este cliente?')) {
      this.clienteService.delete(id).subscribe({
        next: () => this.loadClientes(),
        error: (err) => console.error('Erro ao excluir', err)
      });
    }
  }

  // Filter and Pagination Getters
  get clientesFiltrados(): Cliente[] {
    return this.clientes.filter(c => {
      const matchIdNome = !this.filtros.idNome || 
                          c.id?.toString().includes(this.filtros.idNome) || 
                          c.nome.toLowerCase().includes(this.filtros.idNome.toLowerCase());
      
      const matchCpf = !this.filtros.cpf || (c.cpf && c.cpf.includes(this.filtros.cpf));
      const matchBairro = !this.filtros.bairro || (c.bairro && c.bairro.toLowerCase().includes(this.filtros.bairro.toLowerCase()));
      
      return matchIdNome && matchCpf && matchBairro;
    });
  }

  get clientesExibidos(): Cliente[] {
    const inicio = (this.currentPage - 1) * this.pageSize;
    return this.clientesFiltrados.slice(inicio, inicio + this.pageSize);
  }

  get totalPaginas(): number {
    return Math.ceil(this.clientesFiltrados.length / this.pageSize) || 1;
  }

  onFilterChange(): void {
    this.currentPage = 1;
  }

  private getEmptyCliente(): Cliente {
    return {
      nome: '',
      logradouro: '',
      bairro: '',
      uf: '',
      telefone: '',
      nomeEntidade: 'N/A',
      referencia: '',
      cpf: '',
      email: '',
      cidade: ''
    };
  }
}
