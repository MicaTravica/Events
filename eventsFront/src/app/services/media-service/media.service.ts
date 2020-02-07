import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { httpOptions, authHttpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.url = environment.restPath + '/media';
  }

  public getMediaForEvent(id: string) {
    return this.http.get(this.url + '/event/' + id, {headers: httpOptions()});
  }

  public getOneMediaForEvent(id: number) {
    return this.http.get(this.url + '/event/one/' + id, {headers: httpOptions()});
  }

  public createMedia(media: any) {
    const token = this.authService.getToken();
    return this.http.post(this.url, media, {headers: authHttpOptions(token)});
  }
}
