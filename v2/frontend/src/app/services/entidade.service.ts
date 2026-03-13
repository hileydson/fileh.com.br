import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Entidade {
  id?: number;
  usuarioId?: number;
  nome: string;
}

@Injectable({
  providedIn: 'root'
})
export class EntidadeService {
  private apiUrl = '/api/entidades';

  constructor(private http: HttpClient) {}

  getAllByTenant(tenantId: number): Observable<Entidade[]> {
    return this.http.get<Entidade[]>(`${this.apiUrl}/tenant/${tenantId}`);
  }

  create(entidade: Entidade): Observable<Entidade> {
    return this.http.post<Entidade>(this.apiUrl, entidade);
  }

  update(id: number, entidade: Entidade): Observable<Entidade> {
    return this.http.put<Entidade>(`${this.apiUrl}/${id}`, entidade);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
