import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../models/user-model/user.model';
import { authHttpOptions, httpOptions } from '../../util/http-util';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth-service/auth.service';
import { ChangePassword } from 'src/app/models/change-password-model/change.password.model';
import { of } from 'rxjs';

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
    return this.http.post(this.usersUrl + '/registration', user,
    {
      headers: httpOptions(),
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


  public changePassword(changePassword: ChangePassword ) {
    return this.http.put(this.usersUrl + '/user/password', changePassword,
      {headers: authHttpOptions(this.authService.getToken()), responseType: 'text'});
  }

  public updateMyData(user: User) {
    return this.http.put(this.usersUrl + '/user', user,
      {headers: authHttpOptions(this.authService.getToken()), responseType: 'text'});
  }
}
