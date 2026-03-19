import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    const ctx = authService.getAuthContext();
    if (ctx && ctx.roles.includes('ROLE_ADMIN') && !ctx.entidadeId) {
      if (state.url !== '/selecionar-entidade') {
         router.navigate(['/selecionar-entidade']);
         return false;
      }
    } else if (state.url === '/selecionar-entidade' || state.url === '/') {
        // Redirection based on roles
        if (ctx?.roles.includes('ROLE_ADMIN')) {
          router.navigate(['/dashboard']);
        } else if (ctx?.roles.includes('ROLE_VENDAS')) {
          router.navigate(['/produtos']);
        } else if (ctx?.roles.includes('ROLE_FLUXO_CAIXA')) {
          router.navigate(['/fluxo-caixa']);
        } else {
          // No access? Logout or error
          authService.logout();
          router.navigate(['/login']);
        }
        return false;
    }
    return true;
  }

  router.navigate(['/login']);
  return false;
};
