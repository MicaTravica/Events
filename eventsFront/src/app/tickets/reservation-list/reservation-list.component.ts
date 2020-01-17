import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/models/ticket-model/ticket.model';
import { TicketService } from 'src/app/services/ticket-service/ticket.service';
import { UserService } from 'src/app/services/user-service/user.service';
import { Router } from '@angular/router';
import { MatCheckboxChange } from '@angular/material';
import { HttpErrorResponse, HttpRequest, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {

  reservations: Ticket[];
  checked = new Map<number, boolean>();

  constructor(
    private ticketService: TicketService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
    this.ticketService.getReservationByUserId(this.userService.getUserFromLocalStorage().id).subscribe(
      (data: Ticket[]) => {
        this.reservations = data;
        for (const i of data) {
          this.checked[i.id] = false;
        }
      }
    );
  }

  showDetails(id: number) {
    this.router.navigate(['/event/' + id]);
  }

  selectAll() {
    // tslint:disable-next-line: forin
    for (const key in this.checked) {
      this.checked[key] = true;
    }
  }

  getSelected(): string[] {
    const ids: string[] = [];
    // tslint:disable-next-line: forin
    for (const key in this.checked) {
      if (this.checked[key]) {
        ids.push(key);
      }
    }
    if (ids.length === 0) {
      console.log('nema rez izabranih');
    }
    return ids;
  }

  // dusan
  buy() {
    const ids: number[] = this.getSelected().map( id => +id);
    this.ticketService.startBuyingProcess(ids).subscribe(
      (res: {status: string, redirect_url: string }) => {
        if (res.status === 'success') {
          this.ticketService.setTicketIdsToLocalStorage(ids);
          this.ticketService.redirectPayPal(res.redirect_url);
        }
      },
      (err: HttpErrorResponse)  => {
        console.log(err.message);
      },
    );
  }

  buyOne(id: number) {
    const ids: number[] = [id];
    this.ticketService.startBuyingProcess(ids).subscribe(
      (res: {status: string, redirect_url: string }) => {
        if (res.status === 'success') {
          this.ticketService.setTicketIdsToLocalStorage(ids);
          this.ticketService.redirectPayPal(res.redirect_url);
        }
      },
      (err: HttpErrorResponse)  => {
        console.log(err.message);
      },
    );
  }

  canceled() {
    const ids: number[] = this.getSelected().map( id => +id);
    this.ticketService.cancelReservations(ids).subscribe(
      (res: Ticket[]) => {
        console.log(res);
        if (res.length > 0) {
          console.log('tickets are successfully canceled');
          res.forEach(t => {
            const indx = this.reservations.findIndex(rt => rt.id === t.id);
            this.reservations.splice(indx, 1);
          });
        }
      });
  }

  cancelOne(id: number) {
    const ids: number[] = [id];
    this.ticketService.cancelReservations(ids).subscribe(
      (res: Ticket[]) => {
        console.log(res);
        if (res.length > 0) {
          res.forEach(t => {
            const indx = this.reservations.findIndex(rt => rt.id === t.id);
            this.reservations.splice(indx, 1);
          });
        }
      });
  }
}
