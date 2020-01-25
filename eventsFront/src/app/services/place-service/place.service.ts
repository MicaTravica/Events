import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { httpOptions, authHttpOptions } from 'src/app/util/http-util';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth-service/auth.service';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  private url: string;

  constructor(
<<<<<<< HEAD
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) { 
    this.url = environment.restPath + '/place';
  }

  public getPlaces() {
    return this.http.get(this.url + "s", authHttpOptions(this.authService.getToken()))
    .pipe(map(
      res => {
        return res;this.http.get(this.url + 's', {headers: httpOptions()});
      }
      ))
    }

  public searchPlaces(name: string, numOfPage: number, sizeOfPage: number) {
    const param = new HttpParams()
      .append('name', name)
      .append('num', numOfPage.toString())
      .append('size', sizeOfPage.toString());
    return this.http.get(this.url + 's/search',
      {
        headers: httpOptions(),
        params: param
      }
    );
  }
}
