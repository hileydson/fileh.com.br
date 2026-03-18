import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const adminGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    const ctx = authService.getAuthContext();
    if (ctx && ctx.roles.includes('ROLE_ADMIN')) {
      return true;
    }
  }

  // If not admin, redirect to a safe page (e.g., products)
  router.navigate(['/produtos']);
  return false;
};
