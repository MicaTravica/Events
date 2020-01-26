import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { authHttpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HallService {

  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.url = environment.restPath;
  }

  public getHalls(placeID: number) {
    const token = this.authService.getToken();
    return this.http.get(this.url + '/placeHalls/' + placeID,
      {
        headers: authHttpOptions(token)
      }
    );
  }
}
