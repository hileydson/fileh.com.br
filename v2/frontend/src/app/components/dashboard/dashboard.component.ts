import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FluxoCaixaService, FluxoCaixa } from '../../services/fluxo-caixa.service';
import { ContaPagarService, ContaPagar } from '../../services/conta-pagar.service';
import { ContaReceberService, ContaReceber } from '../../services/conta-receber.service';
import { AuthService } from '../../services/auth.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  loading = true;
  entidadeId: number | null = null;
  entidadeNome: string = 'Sua Empresa';
  currentDate = new Date();

  // Totals
  fluxoEntradas = 0;
  fluxoSaidas = 0;
  fluxoSaldo = 0;
  
  contasPagarTotal = 0;
  contasReceberTotal = 0;
  contasSaldo = 0;

  // For charts (Simple representation)
  fluxoPercent = 0;
  contasPercent = 0;

  constructor(
    private fluxoService: FluxoCaixaService,
    private pagarService: ContaPagarService,
    private receberService: ContaReceberService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const ctx = this.authService.getAuthContext();
    if (ctx) {
      this.entidadeId = ctx.entidadeId;
      this.entidadeNome = ctx.entidadeNome || 'Sua Empresa';
      this.loadDashboardData();
    }
  }

  loadDashboardData(): void {
    if (!this.entidadeId) {
      this.loading = false;
      return;
    }

    this.loading = true;
    
    // Fetch all relevant data for the entity
    forkJoin({
      fluxo: this.fluxoService.getAllByEntidade(this.entidadeId),
      pagar: this.pagarService.getAllByTenant(this.entidadeId),
      receber: this.receberService.getAllByTenant(this.entidadeId)
    }).subscribe({
      next: (data) => {
        this.calculateTotals(data.fluxo, data.pagar, data.receber);
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar dashboard', err);
        this.loading = false;
      }
    });
  }

  calculateTotals(fluxo: FluxoCaixa[], pagar: ContaPagar[], receber: ContaReceber[]): void {
    const now = new Date();
    const currentMonth = now.getMonth();
    const currentYear = now.getFullYear();

    // Reset totals
    this.fluxoEntradas = 0;
    this.fluxoSaidas = 0;
    this.contasPagarTotal = 0;
    this.contasReceberTotal = 0;

    // Filter and Sum Fluxo de Caixa (current month)
    fluxo.forEach(item => {
      const date = new Date(item.dataCadastro);
      if (date.getMonth() === currentMonth && date.getFullYear() === currentYear) {
        if (item.tipo === 'E') {
          this.fluxoEntradas += item.valor;
        } else if (item.tipo === 'S') {
          this.fluxoSaidas += item.valor;
        }
      }
    });
    this.fluxoSaldo = this.fluxoEntradas - this.fluxoSaidas;

    // Filter and Sum Contas a Pagar (current month)
    pagar.forEach(item => {
      const date = new Date(item.dataVencimento);
      if (date.getMonth() === currentMonth && date.getFullYear() === currentYear) {
        this.contasPagarTotal += item.valor;
      }
    });

    // Filter and Sum Contas a Receber (current month)
    receber.forEach(item => {
      const date = new Date(item.dataVencimento);
      if (date.getMonth() === currentMonth && date.getFullYear() === currentYear) {
        this.contasReceberTotal += item.valor;
      }
    });

    this.contasSaldo = this.contasReceberTotal - this.contasPagarTotal;

    // Calculate percentages for bars
    const fluxTotal = this.fluxoEntradas + this.fluxoSaidas;
    this.fluxoPercent = fluxTotal > 0 ? (this.fluxoEntradas / fluxTotal) * 100 : 50;

    const contasTotal = this.contasReceberTotal + this.contasPagarTotal;
    this.contasPercent = contasTotal > 0 ? (this.contasReceberTotal / contasTotal) * 100 : 50;
  }
}
