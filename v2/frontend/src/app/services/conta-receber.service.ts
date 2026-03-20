import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ContaReceber {
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
  recebido?: boolean;
  tipoContaId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class ContaReceberService {
  private apiUrl = '/api/contas-receber';

  constructor(private http: HttpClient) {}

  getAllByTenant(entidadeId: number): Observable<ContaReceber[]> {
    return this.http.get<ContaReceber[]>(`${this.apiUrl}/tenant/${entidadeId}`);
  }

  create(conta: ContaReceber): Observable<ContaReceber> {
    return this.http.post<ContaReceber>(this.apiUrl, conta);
  }

  update(id: number, conta: ContaReceber): Observable<ContaReceber> {
    return this.http.put<ContaReceber>(`${this.apiUrl}/${id}`, conta);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
