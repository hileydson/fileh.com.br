import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const vendasGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    const ctx = authService.getAuthContext();
    if (ctx && (ctx.roles.includes('ROLE_ADMIN') || ctx.roles.includes('ROLE_VENDAS'))) {
      return true;
    }
  }

  router.navigate(['/']);
  return false;
};
