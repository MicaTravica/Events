import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  public searchPlaces(name: string, numOfPage: number, sizeOfPage: number) {
    return this.http.get(this.url + 's/search?name=' + name + '&num=' + numOfPage + '&size=' + sizeOfPage, httpOptions);
  }
}
