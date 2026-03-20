import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Cliente {
  id?: number;
  entidadeId?: number;
  nome: string;
  logradouro: string;
  bairro: string;
  uf: string;
  telefone: string;
  nomeEntidade: string;
  referencia: string;
  cpf: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = '/api/clientes';

  constructor(private http: HttpClient) {}

  getAllByTenant(entidadeId: number): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/tenant/${entidadeId}`);
  }

  search(entidadeId: number, query: string): Observable<Cliente[]> {
    const params = new HttpParams()
      .set('entidadeId', entidadeId.toString())
      .set('query', query);
    return this.http.get<Cliente[]>(`${this.apiUrl}/search`, { params });
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
