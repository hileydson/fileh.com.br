import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface FormaPagamento {
  id?: number;
  entidadeId?: number;
  descricao: string;
}

@Injectable({
  providedIn: 'root'
})
export class FormaPagamentoService {
  private apiUrl = '/api/formas-pagamento';

  constructor(private http: HttpClient) {}

  getAllByTenant(entidadeId: number): Observable<FormaPagamento[]> {
    return this.http.get<FormaPagamento[]>(`${this.apiUrl}/tenant/${entidadeId}`);
  }

  create(forma: FormaPagamento): Observable<FormaPagamento> {
    return this.http.post<FormaPagamento>(this.apiUrl, forma);
  }

  update(id: number, forma: FormaPagamento): Observable<FormaPagamento> {
    return this.http.put<FormaPagamento>(`${this.apiUrl}/${id}`, forma);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
