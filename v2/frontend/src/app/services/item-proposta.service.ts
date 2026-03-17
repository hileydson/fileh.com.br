import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ItemProposta {
  id?: number;
  propostaId: number;
  descricao: string;
  valor: number;
  quantidade: number;
  valorDesconto: number;
  unidade: string;
}

@Injectable({
  providedIn: 'root'
})
export class ItemPropostaService {
  private apiUrl = '/api/itens-proposta';

  constructor(private http: HttpClient) {}

  getAllByProposta(propostaId: number): Observable<ItemProposta[]> {
    return this.http.get<ItemProposta[]>(`${this.apiUrl}/proposta/${propostaId}`);
  }

  create(item: ItemProposta): Observable<ItemProposta> {
    return this.http.post<ItemProposta>(this.apiUrl, item);
  }

  update(id: number, item: ItemProposta): Observable<ItemProposta> {
    return this.http.put<ItemProposta>(`${this.apiUrl}/${id}`, item);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
