import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface FluxoCaixa {
  id?: number;
  descricao: string;
  entidadeId: number;
  valor: number;
  dataCadastro: string;
  tipo: string;
  formaPagamento?: string;
}

@Injectable({
  providedIn: 'root'
})
export class FluxoCaixaService {
  private apiUrl = '/api/fluxo-caixa';

  constructor(private http: HttpClient) {}

  getAllByEntidade(entidadeId: number, date?: string): Observable<FluxoCaixa[]> {
    let url = `${this.apiUrl}/entidade/${entidadeId}`;
    if (date) {
      url += `?date=${date}`;
    }
    return this.http.get<FluxoCaixa[]>(url);
  }

  create(fluxo: FluxoCaixa): Observable<FluxoCaixa> {
    return this.http.post<FluxoCaixa>(this.apiUrl, fluxo);
  }

  update(id: number, fluxo: FluxoCaixa): Observable<FluxoCaixa> {
    return this.http.put<FluxoCaixa>(`${this.apiUrl}/${id}`, fluxo);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
