import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ContaPagar {
  id?: number;
  entidadeId?: number;
  descricao: string;
  dataVencimento: string;
  valor: number;
  dataCadastro?: string;
  numeroDocumento?: string;
  parcelado?: boolean;
  numeroParcela: number;
  fornecedor: string;
  pago?: boolean;
  tipoConta: string;
}

@Injectable({
  providedIn: 'root'
})
export class ContaPagarService {
  private apiUrl = '/api/contas-pagar';

  constructor(private http: HttpClient) {}

  getAllByTenant(entidadeId: number): Observable<ContaPagar[]> {
    return this.http.get<ContaPagar[]>(`${this.apiUrl}/tenant/${entidadeId}`);
  }

  create(conta: ContaPagar): Observable<ContaPagar> {
    return this.http.post<ContaPagar>(this.apiUrl, conta);
  }

  update(id: number, conta: ContaPagar): Observable<ContaPagar> {
    return this.http.put<ContaPagar>(`${this.apiUrl}/${id}`, conta);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
