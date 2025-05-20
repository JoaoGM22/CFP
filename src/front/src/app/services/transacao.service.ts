import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Transacoes } from '../models/transacoes';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class TransacaoService {

  http = inject(HttpClient);

  private API_BASE = "http://localhost:8080/api/transacoes";

  // Método para extrair o ID do usuário do token
  private getCurrentUserId(): number | null {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decodedToken: any = jwtDecode(token);
        // Ajuste o campo de acordo com a estrutura do seu token
        return decodedToken.userId || decodedToken.id || null;
      } catch (error) {
        console.error('Erro ao decodificar token', error);
        return null;
      }
    }
    return null;
  }

  // Método privado para obter a URL com o ID do usuário
  private getApiUrlWithUserId(): string {
    const userId = this.getCurrentUserId();
    if (!userId) {
      throw new Error('Usuário não autenticado');
    }
    return `${this.API_BASE}/${userId}`;
  }

  constructor() { }

  findAll(): Observable<Transacoes[]>{
    return this.http.get<Transacoes[]>(this.getApiUrlWithUserId());
  }

  deleteById(id: number): Observable<string>{
    return this.http.delete<string>(`${this.getApiUrlWithUserId()}/${id}`, {responseType: 'text' as 'json' });
  }

  save(transacao: Transacoes): Observable<string>{
    return this.http.post<string>(this.getApiUrlWithUserId(), transacao ,{responseType: 'text' as 'json' });
  }

  update(transacao: Transacoes): Observable<string>{

    return this.http.put<string>(this.getApiUrlWithUserId(), transacao ,{responseType: 'text' as 'json' });
  }

  findByid(id: number): Observable<Transacoes>{
    return this.http.get<Transacoes>(`${this.getApiUrlWithUserId()}/${id}`,{responseType: 'text' as 'json' });
  }

}
