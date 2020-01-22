import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth-service/auth.service';
import { authHttpOptions } from 'src/app/util/http-util';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private url: string;

  constructor(private http: HttpClient,
              private authService: AuthService ) {
    this.url = environment.restPath + '/report';
  }

  getProfitEvent(id: number): Observable<any> {
    const param = new HttpParams().set('id', id.toString());
    const token = this.authService.getToken();
    return this.http.get(this.url + '/profit/event',
      {
        headers: authHttpOptions(token),
        params: param
      });
  }

  getProfitTime(id: number, fromDate: Date, toDate: Date): Observable<any> {
    const param = new HttpParams()
      .append('id', id.toString())
      .append('fromDate', fromDate.toUTCString())
      .append('toDate', toDate.toUTCString());
    const token = this.authService.getToken();
    return this.http.get(this.url + '/profit/time',
      {
        headers: authHttpOptions(token),
        params: param
      });
  }

  getAttendanceEvent(id: number): Observable<any> {
    const param = new HttpParams().append('id', id.toString());
    const token = this.authService.getToken();
    return this.http.get(this.url + '/attendance/event',
      {
        headers: authHttpOptions(token),
        params: param
      });
  }

  getAttendanceTime(id: number, fromDate: Date, toDate: Date): Observable<any> {
    const param = new HttpParams()
        .append('id', id.toString())
        .append('fromDate', fromDate.toUTCString())
        .append('toDate', toDate.toUTCString());
    const token = this.authService.getToken();
    return this.http.get(this.url + '/attendance/time',
      {
        headers: authHttpOptions(token),
        params: param
      });
  }

}
