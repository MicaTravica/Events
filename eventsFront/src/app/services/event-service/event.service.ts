import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { authHttpOptions, httpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';
import { EventSearch } from 'src/app/models/event-search-model/event-search.model';
import { AngularFireStorage } from 'angularfire2/storage';
import { switchMap, tap } from 'rxjs/operators';
import { EventEntity } from 'src/app/models/event-model/event.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private afStorage: AngularFireStorage
  ) {
    this.url = environment.restPath + '/event';
  }

  public save(addEventData: EventEntity) {
    const token = this.authService.getToken();
    return this.http.post<EventEntity>(this.url, addEventData, {
      headers: authHttpOptions(token)
    });
  }

  // public uploadFile(file: File) {
  //   const name = Math.random().toString(36).substring(7);
  //   const task = this.afStorage.upload(`/event_media/${name}.png`, file);
  //   const storageRef = this.afStorage.ref(`/event_media/${name}.png`);

  //   return from(task).pipe(
  //     switchMap(() => storageRef.getDownloadURL()),
  //     tap(url => {
  //         return url;
  //     })
  //   );
  // }

  public search(params: EventSearch) {
    return this.http.post(this.url + '/search', params, {headers: httpOptions()});
  }

  public getEvent(id: string) {
    return this.http.get(this.url + '/' + id, {headers: httpOptions()});
  }
}
