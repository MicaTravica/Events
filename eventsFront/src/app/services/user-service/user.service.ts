import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../models/user-model/user.model'
import { Router } from '@angular/router';
import {httpOptions, authHttpOptions} from '../../util/http-util';
import { LoginComponent } from 'src/app/core/login/login.component';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.usersUrl = environment.restPath;
  }

  public me(token) {
    this.http.get(this.usersUrl + "/userme", authHttpOptions(token))
    .subscribe(
      data => {
        localStorage.setItem("user", JSON.stringify(data));
      }
    );
  }

  public save(user: User) {
    console.log(user);
    console.log(httpOptions);
    return this.http.post<User>(this.usersUrl + "/registration", user, httpOptions)
    .subscribe(() => { 
      this.router.navigate(['/login']);
    });
  }

  public getUserFromLocalStorage() {
    let user: User = new User();
    const u = localStorage.getItem('user');
    if (u) {
      user = user.deserialize(u);
    }
    return user;
  }
}
