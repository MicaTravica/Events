import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { httpOptions } from 'src/app/util/http-util';
import { environment } from 'src/environments/environment';

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
export class PlaceService {

  private url: string;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.url = environment.restPath + '/place';
  }

  public getPlaces() {
    return this.http.get(this.url + 's', httpOptions);
  }
}
