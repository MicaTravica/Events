import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/models/ticket-model/ticket.model';
import { TicketService } from 'src/app/services/ticket-service/ticket.service';
import { UserService } from 'src/app/services/user-service/user.service';
import { Router } from '@angular/router';
import { MatCheckboxChange } from '@angular/material';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {

  reservations: Ticket[];
  checked = new Map<number, boolean>();

  constructor(
    private ticketServis: TicketService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
    this.ticketServis.getReservationByUserId(this.userService.getUserFromLocalStorage().id).subscribe(
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

  // dusan
  buy() {
    const ids = [];
    // tslint:disable-next-line: forin
    for (const key in this.checked) {
      if (this.checked[key]) {
        ids.push(key);
      }
    }
    if (ids.length === 0) {
      console.log('nema rez izabranih');
    }
  }

  buyOne(id: number) {
    console.log(id);
  }
}
