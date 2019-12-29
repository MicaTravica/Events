import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../models/user-model/user.model'
import { Router } from '@angular/router';

const httpOptions = {
  headers: new HttpHeaders({
   'Content-Type': 'application/json',
   'Accept': 'application/json'
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

  public login(loginData) {
    return this.http.post(this.usersUrl + "/login", loginData, {responseType: 'text'})
    .subscribe(data => { 
      localStorage.setItem("token", data);
      this.router.navigate(['/homepage']);
    });
  }

  public save(user: User) {
    console.log(user);
    console.log(httpOptions);
    return this.http.post<User>(this.usersUrl + "/registration", user, httpOptions)
    .subscribe(data => { console.log(data) });
  }
}
