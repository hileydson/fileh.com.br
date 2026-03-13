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

  tenantId: number = 1; // Defaulting to 1 for MVP. In reality, decode from JWT token.

  constructor(
    private clienteService: ClienteService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // Extract tenant ID from JWT
    const token = this.authService.getToken();
    if (token) {
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            this.tenantId = payload.tenantId || 1;
        } catch(e) {}
    }
    this.loadClientes();
  }

  loadClientes(): void {
    this.loading = true;
    this.clienteService.getAllByTenant(this.tenantId).subscribe({
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
    this.currentCliente.usuarioId = this.tenantId; // Ensure tenant ID is set

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

  private getEmptyCliente(): Cliente {
    return {
      nome: '',
      logradouro: '',
      bairro: '',
      uf: '',
      telefone: '',
      entidade: 'N/A',
      referencia: '',
      cpf: ''
    };
  }
}
