import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { httpOptions } from 'src/app/util/http-util';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  url: string;

  constructor(
    private http: HttpClient,
  ) {
    this.url = environment.restPath + '/media';
  }

  public getMediaForEvent(id: string) {
    return this.http.get(this.url + '/event/' + id, {headers: httpOptions()});
  }

  public getOneMediaForEvent(id: number) {
    return this.http.get(this.url + '/event/one/' + id, {headers: httpOptions()});
  }
}
