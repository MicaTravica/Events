import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../models/user-model/user.model';
import { Router } from '@angular/router';
import {httpOptions, authHttpOptions} from '../../util/http-util';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth-service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {
    this.usersUrl = environment.restPath;
  }

  public me(token: string) {
    return this.http.get(this.usersUrl + '/userme', authHttpOptions(token));
  }

  public save(user: User) {
    // izmeni ovo djoleeeeeeeeeeeee
    // subscribe se ne radi ovde!
    console.log(user);
    console.log(httpOptions);
    return this.http.post<User>(this.usersUrl + '/registration', user, httpOptions)
    .subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

  public getUserFromLocalStorage() {
    let user: User = new User();
    const u = localStorage.getItem('user');
    if (!u) {
      const token = this.authService.getToken();
      this.me(token).subscribe(
      data => {
        localStorage.setItem('user', JSON.stringify(data));
      });
    }
    user = JSON.parse(localStorage.getItem('user'));
    return user;
  }
}
