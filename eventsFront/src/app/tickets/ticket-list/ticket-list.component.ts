import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/models/ticket-model/ticket.model';
import { TicketService } from 'src/app/services/ticket-service/ticket.service';
import { UserService } from 'src/app/services/user-service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss']
})
export class TicketListComponent implements OnInit {

  tickets: Ticket[];
  totalElements = 0;
  size = 8;
  numOfPage = 0;
  sizes = [8, 16, 32];

  constructor(
    private ticketService: TicketService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
    this.getTickets();
  }

  getTickets() {
    this.ticketService.getTicketByUserId(this.userService.getUserFromLocalStorage().id, this.numOfPage, this.size).subscribe(
      (data: Ticket[]) => {
        // tslint:disable-next-line: no-string-literal
        this.tickets = data['content'];
        // tslint:disable-next-line: no-string-literal
        this.totalElements = data['totalElements'];
        // tslint:disable-next-line: no-string-literal
        this.size = data['size'];
      }
    );
  }

  sizeChanged() {
    this.numOfPage = 0;
    this.totalElements = 0;
    this.getTickets();
  }

  pageChanged(num: number) {
    this.numOfPage = num - 1;
    this.getTickets();
  }

  showDetails(id: number) {
    this.router.navigate(['/event/' + id]);
  }

}
