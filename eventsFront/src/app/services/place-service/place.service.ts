import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { httpOptions } from 'src/app/util/http-util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  private url: string;

  constructor(
    private http: HttpClient
  ) {
    this.url = environment.restPath + '/place';
  }

  public getPlaces() {
    return this.http.get(this.url + 's', httpOptions);
  }
}
