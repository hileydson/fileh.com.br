import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Produto {
  id?: number;
  entidadeId?: number;
  descricao: string;
  sku: string;
  valorVenda: number;
  unidade: string;
  estoque: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {
  private apiUrl = '/api/produtos';

  constructor(private http: HttpClient) {}

  getAllByTenant(entidadeId: number): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}/tenant/${entidadeId}`);
  }

  search(entidadeId: number, query: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}/search?entidadeId=${entidadeId}&query=${query}`);
  }

  create(produto: Produto): Observable<Produto> {
    return this.http.post<Produto>(this.apiUrl, produto);
  }

  update(id: number, produto: Produto): Observable<Produto> {
    return this.http.put<Produto>(`${this.apiUrl}/${id}`, produto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  deleteAll(entidadeId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/all/${entidadeId}`);
  }

  importAdd(entidadeId: number, produtos: Produto[]): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/import/${entidadeId}`, produtos);
  }

  importReplace(entidadeId: number, produtos: Produto[]): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/import-replace/${entidadeId}`, produtos);
  }
}
