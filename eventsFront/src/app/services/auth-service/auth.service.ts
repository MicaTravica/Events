import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private http: HttpClient
  ) { }

  login(auth: any): Observable<any> {
    return this.http.post(environment.restPath + '/login', { username: auth.username, password: auth.password },
      { headers: this.headers, responseType: 'text' });
  }

  isLoggedIn(): boolean {
    if (!localStorage.getItem('user')) {
      return false;
    }
    return true;
  }

  getToken() {
    return localStorage.getItem('token');
  }
}
