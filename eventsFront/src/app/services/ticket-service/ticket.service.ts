import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../user-service/user.service';
import {httpOptions, authHttpOptions} from '../../util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { Ticket } from 'src/app/models/ticket-model/ticket.model';
import { TicketBuyReservation } from 'src/app/models/ticket-buy-reservation-model/ticket.buy.reservation.model';

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
    const payload = new TicketBuyReservation();
    payload.userId = user.id;
    payload.ticketIDs = [ticket.id];
    return this.http.put(environment.restPath + '/reserveTicket', payload, authHttpOptions(token));
  }

  // kad klikne na buy dugme ova se pozove
  // kad vrati rediretkuje ga na odredjenu stranicu da potvrdi kupovinu

  startBuyingProcess(ticket: Ticket) {
    const user = this.userService.getUserFromLocalStorage();
    const token = this.authService.getToken();
    const payload = new TicketBuyReservation();
    payload.userId = user.id;
    payload.ticketIDs = [ticket.id];
    return this.http.put(environment.restPath + '/ticketPaymentCreation', payload, authHttpOptions(token));
  }

  redirectPayPal(url: string) {
    window.open(url, '_blank');
  }

  finishBuyingProcess(payPalPaymentId: string,
                      payPalToken: string, payPalPayerId: string) {
    const user = this.userService.getUserFromLocalStorage();
    const token = this.authService.getToken();
    const payload = new TicketBuyReservation();
    const ticketIds: number[] = this.getTicketIdsFromLocalStorage();

    payload.userId = user.id;
    payload.ticketIDs = ticketIds;
    payload.payPalPaymentID = payPalPaymentId;
    payload.payPalToken = payPalToken;
    payload.payPalPayerID = payPalPayerId;
    return this.http.put(environment.restPath + '/buyTicket', payload, authHttpOptions(token));
  }


  // Helper functions
  setTicketIdsToLocalStorage(ids: number[]) {
    localStorage.setItem('ticketIds', JSON.stringify(ids));
  }

  getTicketIdsFromLocalStorage(): any {
    return JSON.parse(localStorage.getItem('ticketIds'));
  }

  removeTicketIdsFromLocalStorage() {
    localStorage.removeItem('ticketIds');
  }

}
