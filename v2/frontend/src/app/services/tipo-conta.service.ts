import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TipoConta {
  id?: number;
  nome: string;
  entidadeId: number;
}

@Injectable({
  providedIn: 'root'
})
export class TipoContaService {
  private apiUrl = '/api/financial/tipo-conta';

  constructor(private http: HttpClient) { }

  listarPorEntidade(entidadeId: number): Observable<TipoConta[]> {
    return this.http.get<TipoConta[]>(`${this.apiUrl}/entidade/${entidadeId}`);
  }

  salvar(tipoConta: TipoConta): Observable<TipoConta> {
    return this.http.post<TipoConta>(this.apiUrl, tipoConta);
  }

  atualizar(id: number, tipoConta: TipoConta): Observable<TipoConta> {
    return this.http.put<TipoConta>(`${this.apiUrl}/${id}`, tipoConta);
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  buscarPorId(id: number): Observable<TipoConta> {
    return this.http.get<TipoConta>(`${this.apiUrl}/${id}`);
  }
}
