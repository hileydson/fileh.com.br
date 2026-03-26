import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PropostaComercial {
  id?: number;
  entidadeId?: number;
  usuarioId?: number;
  clienteId?: number;
  valorDesconto: number;
  valorTotal: number;
  dataCadastro?: string;
  dataPrevista?: string;
  observacao?: string;
  atendente?: string;
  situacao?: string;
  formaPagamento?: string;
  ativo?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class PropostaComercialService {
  private apiUrl = '/api/propostas';

  constructor(private http: HttpClient) {}

  getAllByTenant(entidadeId: number, ativo: boolean = true): Observable<PropostaComercial[]> {
    return this.http.get<PropostaComercial[]>(`${this.apiUrl}/tenant/${entidadeId}?ativo=${ativo}`);
  }

  getAllGlobal(): Observable<PropostaComercial[]> {
    return this.http.get<PropostaComercial[]>(`${this.apiUrl}/global`);
  }

  create(proposta: PropostaComercial): Observable<PropostaComercial> {
    return this.http.post<PropostaComercial>(this.apiUrl, proposta);
  }

  update(id: number, proposta: PropostaComercial): Observable<PropostaComercial> {
    return this.http.put<PropostaComercial>(`${this.apiUrl}/${id}`, proposta);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
