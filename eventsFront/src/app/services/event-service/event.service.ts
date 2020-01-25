import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpEvent, HttpRequest } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth-service/auth.service';

const httpOptions = {
  headers: new HttpHeaders({
   'Content-Type': 'application/json',
   'Accept': 'application/json'
  })
}

const authHttpOptions = (token) => {
  return {
    headers: new HttpHeaders({
    // 'Content-Type': 'application/json',
    // 'Accept': 'application/json',
    'Authorization': 'Bearer ' + token
   })
  }
}


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
    this.url = 'http://localhost:8080/api';
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

    const newRequest = new HttpRequest('POST', this.url + '/files', data, authHttpOptions(localStorage.getItem("token")));

    return this.http.request(newRequest);
  }
}
