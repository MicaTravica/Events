import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpEvent, HttpRequest } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
   'Content-Type': 'application/json',
   'Accept': 'application/json'
  })
}

const authHttpOptions = (token) => {
  return {
    headers: new HttpHeaders({
    // 'Content-Type': 'application/json',
    // 'Accept': 'application/json',
    'Authorization': 'Bearer ' + token
   })
  }
}


@Injectable({
  providedIn: 'root'
})
export class EventService {

  private url: string;

  constructor(
    private http: HttpClient,
    private router: Router
  ) { 
    this.url = 'http://localhost:8080/api';
  }

  public save(addEventData) {
    console.log(addEventData);
    const token = localStorage.getItem("token");

  }

  public uploadFile(file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();

    data.append('file', file);

    const newRequest = new HttpRequest('POST', this.url + '/files', data, authHttpOptions(localStorage.getItem("token")));

    return this.http.request(newRequest);
  }
}
