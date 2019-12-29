import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../models/user-model/user.model'
import { Router } from '@angular/router';
import { LoginComponent } from 'src/app/core/login/login.component';

const httpOptions = {
  headers: new HttpHeaders({
   'Content-Type': 'application/json',
   'Accept': 'application/json'
  })
}

const authHttpOptions = (token) => {
  return {
    headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer ' + token
   })
  }
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.usersUrl = 'http://localhost:8080/api';
  }

  public login(loginData, loginComponent: LoginComponent) {
    let login = this.http.post(this.usersUrl + "/login", loginData, {responseType: 'text'})
    .subscribe(
      async data => { 
        await localStorage.setItem("token", data);
        const token = localStorage.getItem("token");
        this.me(token);
        this.router.navigate(['/homepage']);
      }
    );

    return login;
  }

  public me(token) {
    this.http.get(this.usersUrl + "/userme", authHttpOptions(token))
    .subscribe(
      data => {
        localStorage.setItem("user", JSON.stringify(data));
      }
    );
  }

  public logout() {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    this.router.navigate(['/login']);
  }

  public save(user: User) {
    console.log(user);
    console.log(httpOptions);
    return this.http.post<User>(this.usersUrl + "/registration", user, httpOptions)
    .subscribe(() => { 
      this.router.navigate(['/login']);
     });
  }
}
