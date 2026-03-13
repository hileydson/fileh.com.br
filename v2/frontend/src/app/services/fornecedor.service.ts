import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Fornecedor {
  id?: number;
  usuarioId?: number;
  nome: string;
  cnpj?: string;
  logradouro?: string;
  bairro?: string;
  cidade?: string;
  cep?: string;
  inscricaoEstadual?: string;
  inscricaoMunicipal?: string;
  contato?: string;
}

@Injectable({
  providedIn: 'root'
})
export class FornecedorService {
  private apiUrl = '/api/fornecedores';

  constructor(private http: HttpClient) {}

  getAllByTenant(tenantId: number): Observable<Fornecedor[]> {
    return this.http.get<Fornecedor[]>(`${this.apiUrl}/tenant/${tenantId}`);
  }

  create(fornecedor: Fornecedor): Observable<Fornecedor> {
    return this.http.post<Fornecedor>(this.apiUrl, fornecedor);
  }

  update(id: number, fornecedor: Fornecedor): Observable<Fornecedor> {
    return this.http.put<Fornecedor>(`${this.apiUrl}/${id}`, fornecedor);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
