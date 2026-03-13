import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Cliente {
  id?: number;
  usuarioId?: number;
  nome: string;
  logradouro: string;
  bairro: string;
  uf: string;
  telefone: string;
  entidade: string;
  referencia: string;
  cpf: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = '/api/clientes';

  constructor(private http: HttpClient) {}

  getAllByTenant(tenantId: number): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/tenant/${tenantId}`);
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.apiUrl, cliente);
  }

  update(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.apiUrl}/${id}`, cliente);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
