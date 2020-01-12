import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { authHttpOptions, httpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';
import { EventSearch } from 'src/app/models/event-search-model/event-search.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.url = environment.restPath + '/event';
  }

  // zameni any
  public save(addEventData: any) {
    console.log(addEventData);
    const token = this.authService.getToken();

  }

  public uploadFile(file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();

    data.append('file', file);

    const newRequest = new HttpRequest('POST', this.url + '/files', data, authHttpOptions(this.authService.getToken()));

    return this.http.request(newRequest);
  }

  public search(params: EventSearch) {
     return this.http.post(this.url + '/search', params, httpOptions);
  }

  public getEvent(id: string) {
     return this.http.get(this.url + '/' + id, httpOptions);
  }
}
