import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FluxoCaixaService, FluxoCaixa } from '../../services/fluxo-caixa.service';
import { ContaPagarService, ContaPagar } from '../../services/conta-pagar.service';
import { ContaReceberService, ContaReceber } from '../../services/conta-receber.service';
import { AuthService } from '../../services/auth.service';
import { forkJoin } from 'rxjs';

import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  loading = true;
  entidadeId: number | null = null;
  entidadeNome: string = 'Sua Empresa';
  currentDate = new Date();
  
  selectedMonth: number;
  selectedYear: number;
  months = [
    { value: 0, label: 'Janeiro' },
    { value: 1, label: 'Fevereiro' },
    { value: 2, label: 'Março' },
    { value: 3, label: 'Abril' },
    { value: 4, label: 'Maio' },
    { value: 5, label: 'Junho' },
    { value: 6, label: 'Julho' },
    { value: 7, label: 'Agosto' },
    { value: 8, label: 'Setembro' },
    { value: 9, label: 'Outubro' },
    { value: 10, label: 'Novembro' },
    { value: 11, label: 'Dezembro' }
  ];
  years: number[] = [];

  rawData: { fluxo: FluxoCaixa[], pagar: ContaPagar[], receber: ContaReceber[] } | null = null;

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
  ) {
    const now = new Date();
    this.selectedMonth = now.getMonth();
    this.selectedYear = now.getFullYear();
    
    // Generate years from 2024 to now+1
    const startYear = 2024;
    const endYear = now.getFullYear() + 1;
    for (let y = startYear; y <= endYear; y++) {
      this.years.push(y);
    }
  }

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
        this.rawData = data;
        this.calculateTotals();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar dashboard', err);
        this.loading = false;
      }
    });
  }

  onPeriodChange(): void {
    this.calculateTotals();
  }

  calculateTotals(): void {
    if (!this.rawData) return;
    const { fluxo, pagar, receber } = this.rawData;
    
    const targetMonth = this.selectedMonth;
    const targetYear = this.selectedYear;

    // Reset totals
    this.fluxoEntradas = 0;
    this.fluxoSaidas = 0;
    this.contasPagarTotal = 0;
    this.contasReceberTotal = 0;

    const parseDateStr = (dateStr: any) => {
      if (!dateStr || typeof dateStr !== 'string') return { y: -1, m: -1 };
      const parts = dateStr.includes('-') ? dateStr.split('-') : dateStr.split('/');
      if (parts.length < 2) return { y: -1, m: -1 };
      
      let y, m;
      if (parts[0].length === 4) {
        // YYYY-MM-DD
        y = parseInt(parts[0], 10);
        m = parseInt(parts[1], 10) - 1;
      } else {
        // DD/MM/YYYY
        y = parseInt(parts[2], 10);
        m = parseInt(parts[1], 10) - 1;
      }
      return { y, m };
    };

    // Filter and Sum Fluxo de Caixa
    fluxo.forEach(item => {
      const { y, m } = parseDateStr(item.dataCadastro);
      if (m == targetMonth && y == targetYear) {
        if (item.tipo === 'E') {
          this.fluxoEntradas += item.valor;
        } else if (item.tipo === 'S') {
          this.fluxoSaidas += item.valor;
        }
      }
    });
    this.fluxoSaldo = this.fluxoEntradas - this.fluxoSaidas;

    // Filter and Sum Contas a Pagar
    pagar.forEach(item => {
      const { y, m } = parseDateStr(item.dataVencimento);
      if (m == targetMonth && y == targetYear) {
        this.contasPagarTotal += item.valor;
      }
    });

    // Filter and Sum Contas a Receber
    receber.forEach(item => {
      const { y, m } = parseDateStr(item.dataVencimento);
      if (m == targetMonth && y == targetYear) {
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
