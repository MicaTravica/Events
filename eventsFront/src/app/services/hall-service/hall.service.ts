import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { authHttpOptions, httpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';
import { Hall } from 'src/app/models/hall-model/hall.model';

@Injectable({
  providedIn: 'root'
})
export class HallService {

  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.url = environment.restPath  + '/hall';
  }

  public add(hall: Hall) {
    const token = this.authService.getToken();
    return this.http.post(this.url, hall, {headers: authHttpOptions(token)});
  }

  public update(hall: Hall) {
    const token = this.authService.getToken();
    return this.http.put(this.url, hall, {headers: authHttpOptions(token)});
  }

  public getHall(id: string) {
    return this.http.get(this.url + '/' + id, {headers: httpOptions()});
  }

  public getHalls(placeID: number) {
    const token = this.authService.getToken();
    return this.http.get(this.url + '/placeHalls/' + placeID,
      {
        headers: new HttpHeaders().set('Authorization',  `Bearer ${token}`)
      }
    );
  }
}
