import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { authHttpOptions, httpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';
import { EventSearch } from 'src/app/models/event-search-model/event-search.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) { 
    this.url = environment.restPath + '/event';
  }

  public save(addEventData, selectedSectors) {
    addEventData.eventState = "AVAILABLE";

    const halls = addEventData.halls.map((hall) => {
      return {id: hall, place: { id: addEventData.place }, sectors: []}
    })

    const updatedHalls = halls.map((hall) => {
      selectedSectors.forEach(item => {
        if (item.hallID == hall.id) {
          hall.sectors.push({id: item.id})
        }
      })
      return hall;
    })

    addEventData.halls = updatedHalls;
    delete addEventData.sectors;
    delete addEventData.photos;
    delete addEventData.videos;
    delete addEventData.place;
    delete addEventData.ticketPrice;

    return this.http.post<any>(this.url + '/event', addEventData, authHttpOptions(localStorage.getItem("token")))
    .subscribe(data => {
      console.log(data);
    });
  }

  public uploadFile(file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();

    data.append('file', file);

    const newRequest = new HttpRequest('POST', this.url + '/files', data,
      {
        headers: authHttpOptions(this.authService.getToken())
      }
    );
    return this.http.request(newRequest);
  }

  public search(params: EventSearch) {
    return this.http.post(this.url + '/search', params, {headers: httpOptions()});
  }

  public getEvent(id: string) {
    return this.http.get(this.url + '/' + id, {headers: httpOptions()});
  }
}
