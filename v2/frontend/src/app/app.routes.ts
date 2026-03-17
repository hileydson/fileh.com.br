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
import { SelecionarEntidadeComponent } from './components/selecionar-entidade/selecionar-entidade.component';
import { VendasConfigComponent } from './components/vendas-config/vendas-config.component';
import { SubUsuarioListComponent } from './components/subusuario-list/subusuario-list.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'selecionar-entidade', component: SelecionarEntidadeComponent, canActivate: [authGuard] },
    { 
        path: '', 
        component: LayoutComponent, 
        canActivate: [authGuard],
        children: [
            { path: 'clientes', component: ClienteListComponent },
            { path: 'entidades', component: EntidadeListComponent },
            { path: 'fornecedores', component: FornecedorListComponent },
            { path: 'propostas', component: PropostaComercialListComponent },
            { path: 'contas-pagar', component: ContaPagarListComponent },
            { path: 'contas-receber', component: ContaReceberListComponent },
            { path: 'fluxo-caixa', component: FluxoCaixaListComponent },
            { path: 'vendas-config', component: VendasConfigComponent },
            { path: 'usuarios', component: SubUsuarioListComponent },
            { path: '', redirectTo: 'clientes', pathMatch: 'full' }
        ]
    },
    { path: '**', redirectTo: '' }
];
