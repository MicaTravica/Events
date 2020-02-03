import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest, HttpHeaders } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { authHttpOptions, httpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';
import { EventSearch } from 'src/app/models/event-search-model/event-search.model';
import { Router } from '@angular/router';
import { AngularFireStorage } from 'angularfire2/storage';
import { switchMap, tap, mergeMap, finalize } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    private afStorage: AngularFireStorage
  ) {
    this.url = environment.restPath + '/event';
  }

  public save(addEventData, selectedSectors) {
    addEventData.eventState = 'AVAILABLE';

    const halls = addEventData.halls.map((hall) => {
      return {id: hall, place: { id: addEventData.place }, sectors: []};
    });

    const updatedHalls = halls.map((hall) => {
      selectedSectors.forEach(item => {
        if (item.hallID === hall.id) {
          hall.sectors.push({id: item.id});
        }
      });
      return hall;
    });

    const priceList = selectedSectors.map(sector => {
      return {
        sectorId: sector.id,
        price: addEventData.ticketPrice
      };
    });

    addEventData.halls = updatedHalls;
    addEventData.priceList = priceList;
    delete addEventData.sectors;
    delete addEventData.photos;
    delete addEventData.videos;
    delete addEventData.place;
    delete addEventData.ticketPrice;

    const token = this.authService.getToken();

    console.log(addEventData);

    return this.http.post<any>(this.url, addEventData, {
      headers: new HttpHeaders().set('Authorization',  `Bearer ${token}`)
    });
  }

  public uploadFile(file: File) {
    const task = this.afStorage.upload('/eventID/image.png', file);
    const storageRef = this.afStorage.ref('/eventID/image.png');

    return from(task).pipe(
      switchMap(() => storageRef.getDownloadURL()),
      tap(url => {
          return url;
      })
    )
  }

  public search(params: EventSearch) {
    return this.http.post(this.url + '/search', params, {headers: httpOptions()});
  }

  public getEvent(id: string) {
    return this.http.get(this.url + '/' + id, {headers: httpOptions()});
  }
}
