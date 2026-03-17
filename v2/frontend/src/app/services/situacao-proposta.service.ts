import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SituacaoProposta {
  id?: number;
  entidadeId?: number;
  descricao: string;
}

@Injectable({
  providedIn: 'root'
})
export class SituacaoPropostaService {
  private apiUrl = '/api/situacoes-proposta';

  constructor(private http: HttpClient) {}

  getAllByTenant(entidadeId: number): Observable<SituacaoProposta[]> {
    return this.http.get<SituacaoProposta[]>(`${this.apiUrl}/tenant/${entidadeId}`);
  }

  create(situacao: SituacaoProposta): Observable<SituacaoProposta> {
    return this.http.post<SituacaoProposta>(this.apiUrl, situacao);
  }

  update(id: number, situacao: SituacaoProposta): Observable<SituacaoProposta> {
    return this.http.put<SituacaoProposta>(`${this.apiUrl}/${id}`, situacao);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
