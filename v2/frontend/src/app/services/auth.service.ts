import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, tap, Observable } from 'rxjs';

export interface AuthContext {
  id: number;
  tenantId: number;
  entidadeId: number | null;
  entidadeNome?: string | null;
  username: string;
  nome: string;
  email: string;
  roles: string[];
  msgFooter?: string;
  isDefaultPassword?: boolean;
  permiteImportarCSV?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = '/api/auth';
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/signin`, credentials).pipe(
      tap((res: any) => {
        if (res && res.token) {
          localStorage.setItem('jwt_token', res.token);
          
          const authContext: AuthContext = {
            id: res.id,
            tenantId: res.tenantId,
            entidadeId: res.entidadeId !== undefined ? res.entidadeId : null,
            username: res.username,
            nome: res.name,
            email: res.email,
            roles: res.roles || [],
            msgFooter: res.msgFooter,
            isDefaultPassword: res.isDefaultPassword,
            entidadeNome: res.entidadeNome,
            permiteImportarCSV: res.permiteImportarCSV
          };
          localStorage.setItem('auth_context', JSON.stringify(authContext));
          
          this.loggedIn.next(true);
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('auth_context');
    this.loggedIn.next(false);
  }

  isLoggedIn(): boolean {
    return this.loggedIn.value;
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('jwt_token');
  }

  getAuthContext(): AuthContext | null {
    const ctx = localStorage.getItem('auth_context');
    return ctx ? JSON.parse(ctx) : null;
  }

  updateEntidadeId(entidadeId: number, nome: string): void {
    const ctx = this.getAuthContext();
    if (ctx) {
      ctx.entidadeId = entidadeId;
      ctx.entidadeNome = nome;
      localStorage.setItem('auth_context', JSON.stringify(ctx));
    }
  }

  updateProfile(details: any): Observable<any> {
    const ctx = this.getAuthContext();
    if (!ctx) return new Observable(obs => obs.error('No session'));
    
    return this.http.patch(`/api/auth/subusuarios/profile/${ctx.id}`, details).pipe(
      tap((res: any) => {
        if (res) {
          ctx.nome = res.nome || ctx.nome;
          ctx.msgFooter = res.msgFooter !== undefined ? res.msgFooter : ctx.msgFooter;
          if (details.senha) {
            ctx.isDefaultPassword = false;
          }
          localStorage.setItem('auth_context', JSON.stringify(ctx));
        }
      })
    );
  }

  switchEntidade(entidadeId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/switch-entidade/${entidadeId}`, {}).pipe(
      tap((res: any) => {
        if (res && res.token) {
          localStorage.setItem('jwt_token', res.token);
          
          const authContext: AuthContext = {
            id: res.id,
            tenantId: res.tenantId,
            entidadeId: res.entidadeId,
            username: res.username,
            nome: res.name,
            email: res.email,
            roles: res.roles || [],
            msgFooter: res.msgFooter,
            isDefaultPassword: res.isDefaultPassword,
            entidadeNome: res.entidadeNome,
            permiteImportarCSV: res.permiteImportarCSV
          };
          localStorage.setItem('auth_context', JSON.stringify(authContext));
          
          this.loggedIn.next(true);
        }
      })
    );
  }
}
