import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../user-service/user.service';
import {httpOptions, authHttpOptions} from '../../util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { Ticket } from 'src/app/models/ticket-model/ticket.model';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http: HttpClient,
              private userService: UserService,
              private authService: AuthService) {}

  getAllByEventId(eventId: number) {
    const token = this.authService.getToken();
    return this.http.get(environment.restPath + '/ticketsForEvent/' + eventId, authHttpOptions(token));
  }

  makeReservation(ticket: Ticket) {
    const user = this.userService.getUserFromLocalStorage();
    const token = this.authService.getToken();
    const payload = ticket;
    payload.userId = user.id;
    return this.http.put(environment.restPath + '/reserveTicket', payload, authHttpOptions(token));
  }

  // kad klikne na buy dugme ova se pozove
  // kad vrati rediretkuje ga na odredjenu stranicu da potvrdi kupovinu

  startBuyingProcess(ticket: Ticket) {
    const user = this.userService.getUserFromLocalStorage();
    const token = this.authService.getToken();
    const payload = ticket;
    payload.userId = user.id;
    return this.http.put(environment.restPath + '/ticketPaymentCreation', payload, authHttpOptions(token));
  }

  redirectPayPal(url: string) {
    window.open(url, '_blank');
  }

  finishBuyingProcess(ticketId: number, payPalPaymentId: string,
                      payPalToken: string, payPalPayerId: string) {
    const user = this.userService.getUserFromLocalStorage();
    const token = this.authService.getToken();
    const payload = new Ticket();
    payload.userId = user.id;
    payload.id = ticketId;
    payload.payPalPaymentID = payPalPaymentId;
    payload.payPalToken = payPalToken;
    payload.payPalPayerID = payPalPayerId;
    return this.http.put(environment.restPath + '/buyTicket', payload, authHttpOptions(token));
  }

}
