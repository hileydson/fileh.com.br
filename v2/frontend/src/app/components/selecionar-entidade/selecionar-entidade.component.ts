import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EntidadeService } from '../../services/entidade.service';

@Component({
  selector: 'app-selecionar-entidade',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './selecionar-entidade.component.html',
  styleUrls: ['./selecionar-entidade.component.scss']
})
export class SelecionarEntidadeComponent implements OnInit {
  entidades: any[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private entidadeService: EntidadeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (!ctx || !ctx.roles.includes('ROLE_ADMIN')) {
      this.router.navigate(['/']);
      return;
    }

    if (ctx.entidadeId) {
      this.router.navigate(['/']);
      return;
    }

    this.entidadeService.getAllByTenant(ctx.tenantId).subscribe({
      next: (data: any[]) => {
        this.entidades = data;
        this.loading = false;
        if (this.entidades.length === 0) {
          this.errorMessage = 'Nenhuma entidade encontrada para este Usuário/Empresa. Por favor, contate o suporte.';
        }
      },
      error: (err: any) => {
        this.errorMessage = 'Erro ao buscar as identidades disponíveis.';
        this.loading = false;
      }
    });
  }

  selecionarEntidade(entidadeId: number): void {
    const entidade = this.entidades.find(e => e.id === entidadeId);
    if (entidade) {
        this.authService.updateEntidadeId(entidadeId, entidade.descricao || entidade.nome);
    }
    this.router.navigate(['/']);
  }
}
