import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { httpOptions, authHttpOptions } from 'src/app/util/http-util';
import { environment } from 'src/environments/environment';
import { PlaceSearch } from 'src/app/models/place-search-model/place-search.model';
import { Place } from 'src/app/models/place-model/place.model';
import { AuthService } from '../auth-service/auth.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {


  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.url = environment.restPath + '/place';
  }

  public getPlaces() {
    return this.http.get(this.url + 's', {
      headers: authHttpOptions(this.authService.getToken())
    })
    .pipe(map(
      res => {
        return res;
      }
      ));
    }

  public getPlace(id: string) {
    return this.http.get(this.url + '/' + id, {headers: httpOptions()});
  }

  public search(params: PlaceSearch) {
    return  this.http.post(this.url + '/search', params , {headers: httpOptions()});
  }

  public add(place: Place) {
    const token = this.authService.getToken();
    return this.http.post(this.url, place, {headers: authHttpOptions(token)});
  }

  public update(place: Place) {
    const token = this.authService.getToken();
    return this.http.put(this.url, place, {headers: authHttpOptions(token)});
  }


}
