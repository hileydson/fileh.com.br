import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { LayoutComponent } from './components/layout/layout.component';
import { ClienteListComponent } from './components/cliente-list/cliente-list.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { 
        path: '', 
        component: LayoutComponent, 
        canActivate: [authGuard],
        children: [
            { path: 'clientes', component: ClienteListComponent },
            { path: '', redirectTo: 'clientes', pathMatch: 'full' }
        ]
    },
    { path: '**', redirectTo: '' }
];
