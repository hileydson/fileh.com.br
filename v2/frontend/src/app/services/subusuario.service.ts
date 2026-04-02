import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SubUsuario {
  id?: number;
  usuarioId?: number;
  entidadeId?: number;
  nome: string;
  login: string;
  senha?: string;
  moduloVenda: boolean;
  isAdm: boolean;
  moduloCaixa: boolean;
  permiteImportarCSV: boolean;
  msgFooter?: string;
}

@Injectable({
  providedIn: 'root'
})
export class SubUsuarioService {
  private apiUrl = '/api/auth/subusuarios';

  constructor(private http: HttpClient) {}

  getAllByTenant(tenantId: number): Observable<SubUsuario[]> {
    return this.http.get<SubUsuario[]>(`${this.apiUrl}/tenant/${tenantId}`);
  }

  create(tenantId: number, subUsuario: SubUsuario): Observable<SubUsuario> {
    return this.http.post<SubUsuario>(`${this.apiUrl}/${tenantId}`, subUsuario);
  }

  update(id: number, subUsuario: SubUsuario): Observable<SubUsuario> {
    return this.http.put<SubUsuario>(`${this.apiUrl}/${id}`, subUsuario);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
