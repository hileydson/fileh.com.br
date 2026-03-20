import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { LayoutComponent } from './components/layout/layout.component';
import { ClienteListComponent } from './components/cliente-list/cliente-list.component';
import { EntidadeListComponent } from './components/entidade-list/entidade-list.component';
import { FornecedorListComponent } from './components/fornecedor-list/fornecedor-list.component';
import { PropostaComercialListComponent } from './components/proposta-comercial-list/proposta-comercial-list.component';
import { ContaPagarListComponent } from './components/conta-pagar-list/conta-pagar-list.component';
import { ContaReceberListComponent } from './components/conta-receber-list/conta-receber-list.component';
import { FluxoCaixaListComponent } from './components/fluxo-caixa-list/fluxo-caixa-list.component';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { vendasGuard } from './guards/vendas.guard';
import { fluxoCaixaGuard } from './guards/fluxo-caixa.guard';
import { SelecionarEntidadeComponent } from './components/selecionar-entidade/selecionar-entidade.component';
import { VendasConfigComponent } from './components/vendas-config/vendas-config.component';
import { SubUsuarioListComponent } from './components/subusuario-list/subusuario-list.component';
import { ProdutoListComponent } from './components/produto-list/produto-list.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FinanceiroConfigComponent } from './components/financeiro-config/financeiro-config.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'selecionar-entidade', component: SelecionarEntidadeComponent, canActivate: [authGuard] },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent, canActivate: [adminGuard] },
      { path: 'fluxo-caixa', component: FluxoCaixaListComponent, canActivate: [fluxoCaixaGuard] },
      { path: 'propostas', component: PropostaComercialListComponent, canActivate: [vendasGuard] },
      { path: 'clientes', component: ClienteListComponent, canActivate: [vendasGuard] },
      { path: 'fornecedores', component: FornecedorListComponent, canActivate: [adminGuard] },
      { path: 'entidades', component: EntidadeListComponent, canActivate: [adminGuard] },
      { path: 'contas-pagar', component: ContaPagarListComponent, canActivate: [adminGuard] },
      { path: 'contas-receber', component: ContaReceberListComponent, canActivate: [adminGuard] },
      { path: 'usuarios', component: SubUsuarioListComponent, canActivate: [adminGuard] },
      { path: 'produtos', component: ProdutoListComponent, canActivate: [vendasGuard] },
      { path: 'vendas-config', component: VendasConfigComponent, canActivate: [adminGuard] },
      { path: 'financeiro-config', component: FinanceiroConfigComponent, canActivate: [adminGuard] }
    ]
  },
  { path: '**', redirectTo: '' }
];
