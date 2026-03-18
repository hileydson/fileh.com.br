import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface FormaPagamento {
  id?: number;
  entidadeId?: number;
  descricao: string;
  tipo?: string;
}

@Injectable({
  providedIn: 'root'
})
export class FormaPagamentoService {
  private apiUrl = '/api/formas-pagamento';

  constructor(private http: HttpClient) {}

  getAllByTenant(entidadeId: number, tipo?: string): Observable<FormaPagamento[]> {
    let url = `${this.apiUrl}/tenant/${entidadeId}`;
    if (tipo) {
      url += `?tipo=${tipo}`;
    }
    return this.http.get<FormaPagamento[]>(url);
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
