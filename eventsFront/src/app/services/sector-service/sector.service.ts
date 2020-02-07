import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { httpOptions, authHttpOptions } from 'src/app/util/http-util';
import { environment } from 'src/environments/environment';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { AuthService } from '../auth-service/auth.service';


@Injectable({
    providedIn: 'root'
  })
  export class SectorService {

    private url: string;

    constructor(
      private http: HttpClient,
      private authService: AuthService
    ) {
      this.url = environment.restPath + '/sector';
    }

    public add(sector: Sector) {
      const token = this.authService.getToken();
      return this.http.post(this.url, sector, {headers: authHttpOptions(token)});
    }

    public update(sector: Sector) {
      const token = this.authService.getToken();
      return this.http.put(this.url, sector, {headers: authHttpOptions(token)});
    }

   public getSector(id: string) {
    return this.http.get(this.url + '/' + id, {headers: httpOptions()});
  }

}
