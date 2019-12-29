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

const authHttpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token')
   })
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
    return this.http.post(this.usersUrl + "/login", loginData, httpOptions)
    .subscribe(
      data => { 
        localStorage.setItem("user", JSON.stringify(data['user']));
        localStorage.setItem("token", data['value']);
        this.router.navigate(['/homepage']);
      },
      err => {
        loginComponent.setError(err.status);
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
