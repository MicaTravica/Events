import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../models/user-model/user.model';
import { httpOptionsText, authHttpOptions } from '../../util/http-util';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth-service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.usersUrl = environment.restPath;
  }

  public me(token: string) {
    return this.http.get(this.usersUrl + '/userme', { headers: authHttpOptions(token)});
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl + '/registration', user, 
    {
      headers: httpOptionsText(),
      responseType: 'text'
    });
  }

  public getUserFromLocalStorage() {
    let user: User = null;
    const u = localStorage.getItem('user');
    if (u) {
      user = JSON.parse(localStorage.getItem('user'));
    }
    return user;
  }
}
