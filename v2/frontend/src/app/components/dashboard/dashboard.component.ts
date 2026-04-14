import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FluxoCaixaService, FluxoCaixa } from '../../services/fluxo-caixa.service';
import { ContaPagarService, ContaPagar } from '../../services/conta-pagar.service';
import { ContaReceberService, ContaReceber } from '../../services/conta-receber.service';
import { AuthService } from '../../services/auth.service';
import { forkJoin } from 'rxjs';

import { FormsModule } from '@angular/forms';
import { DateInputComponent } from '../shared/date-input/date-input.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, DateInputComponent],
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

  filterMode: 'month' | 'period' = 'month';
  startDate: string = '';
  endDate: string = '';

  rawData: { fluxo: FluxoCaixa[], pagar: ContaPagar[], receber: ContaReceber[] } | null = null;
  // ... rest of the properties ...
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

  fluxoBreakdown: { label: string, value: number }[] = [];

  constructor(
    private fluxoService: FluxoCaixaService,
    private pagarService: ContaPagarService,
    private receberService: ContaReceberService,
    private authService: AuthService
  ) {
    const now = new Date();
    this.selectedMonth = now.getMonth();
    this.selectedYear = now.getFullYear();
    
    // Default period: Current month
    const firstDay = new Date(now.getFullYear(), now.getMonth(), 1);
    const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0);
    this.startDate = firstDay.toISOString().split('T')[0];
    this.endDate = lastDay.toISOString().split('T')[0];

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
    this.fluxoBreakdown = [];

    const parseDate = (dateVal: any) => {
      if (!dateVal) return null;
      
      // If it's already a string, parse it
      if (typeof dateVal === 'string') {
        // Handle ISO with time or just date
        const dateStr = dateVal.includes('T') ? dateVal.split('T')[0] : dateVal;
        const parts = dateStr.includes('-') ? dateStr.split('-') : dateStr.split('/');
        
        if (parts.length < 3) return null;
        
        let y, m, d;
        if (parts[0].length === 4) {
          // YYYY-MM-DD
          y = parseInt(parts[0], 10);
          m = parseInt(parts[1], 10) - 1;
          d = parseInt(parts[2], 10);
        } else {
          // DD/MM/YYYY
          y = parseInt(parts[2], 10);
          m = parseInt(parts[1], 10) - 1;
          d = parseInt(parts[0], 10);
        }
        return new Date(y, m, d);
      }
      
      // If it's an array [Y, M, D]
      if (Array.isArray(dateVal) && dateVal.length >= 3) {
        return new Date(dateVal[0], dateVal[1] - 1, dateVal[2]);
      }
      
      return null;
    };

    const isMatch = (dateVal: any) => {
      const d = parseDate(dateVal);
      if (!d) return false;

      if (this.filterMode === 'month') {
        return d.getMonth() === targetMonth && d.getFullYear() === targetYear;
      } else {
        const start = this.startDate ? new Date(this.startDate + 'T00:00:00') : null;
        const end = this.endDate ? new Date(this.endDate + 'T23:59:59') : null;
        
        if (start && d < start) return false;
        if (end && d > end) return false;
        return true;
      }
    };

    const breakdownMap: { [key: string]: number } = {};

    // Filter and Sum Fluxo de Caixa
    fluxo.forEach(item => {
      if (isMatch(item.dataCadastro)) {
        const val = item.valor || 0;
        const forma = item.formaPagamento || 'Não Informado';
        
        if (item.tipo === 'EN') { // Updated to match list component EN
          this.fluxoEntradas += val;
          breakdownMap[forma] = (breakdownMap[forma] || 0) + val;
        } else if (item.tipo === 'SA') { // Updated to match list component SA
          this.fluxoSaidas += val;
          breakdownMap[forma] = (breakdownMap[forma] || 0) - val;
        }
      }
    });

    // Convert map to array for display
    this.fluxoBreakdown = Object.keys(breakdownMap).map(key => ({
      label: key,
      value: breakdownMap[key]
    })).sort((a, b) => b.value - a.value);

    this.fluxoSaldo = this.fluxoEntradas - this.fluxoSaidas;

    // Filter and Sum Contas a Pagar
    pagar.forEach(item => {
      if (isMatch(item.dataVencimento)) {
        this.contasPagarTotal += item.valor;
      }
    });

    // Filter and Sum Contas a Receber
    receber.forEach(item => {
      if (isMatch(item.dataVencimento)) {
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
