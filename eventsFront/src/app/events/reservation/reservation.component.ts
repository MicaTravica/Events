import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from '../../services/event-service/event.service';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { HttpErrorResponse } from '@angular/common/http';
import { TicketService } from 'src/app/services/ticket-service/ticket.service';
import { Ticket } from 'src/app/models/ticket-model/ticket.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit {

  event: EventEntity = new EventEntity();
  hall: any = {};
  hallChoosen = false;
  sector: any = {};
  tickets: Ticket[] = [];
  ticketsFiltered: Ticket[] = [];

  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private ticketService: TicketService,
    private toastr: ToastrService) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.eventService.getEvent(id).subscribe(
        (data: EventEntity) => {
          this.event = data;
        }
        , (error: HttpErrorResponse) => {
          console.log(error.message);
        },
        () => {
          this.ticketService.getAllByEventId(this.event.id).subscribe(
            (tickets: any[]) => {
              this.tickets = tickets;
            },
            (err: HttpErrorResponse)  =>{
              console.log(err.message);
            }
          )
        }
      );
    }
  }

  chooseHall(hallId: number) {
    if (hallId !== -1) {
      this.hallChoosen = true;
      this.hall = this.event.halls.find(h => h.id === hallId);
    } else {
      this.hallChoosen = false;
      this.hall = {};
    }
  }

  // treba da se proveri sta je zauzeto u sektoru..
  chooseSector(sectorId: number) {
    if (sectorId !== -1) {
      this.sector = this.hall.sectors.find(s => s.id === sectorId);
      this.ticketsFiltered = this.tickets.filter(t => 
          t.sectorName === this.sector.name && t.hallName === this.hall.name
        );
    } else {
      this.ticketsFiltered = []
      this.sector = {};
    }
  }

  // ticket vec postoji da li postoji nacin da ih dobavim 
  reserveTicket(ticket: Ticket) {
    this.ticketService.makeReservation(ticket).subscribe(
      (res: Ticket) => {
        this.toastr.success('reservation successfully made');
        this.updateTicketInLists(res);
      },
      (err: HttpErrorResponse)  => {
        console.log(err.message);
      }
    );
  }

  buyTicket(ticket: Ticket) {
    this.ticketService.startBuyingProcess(ticket).subscribe(
      (res: Ticket) => {
        this.toastr.success('first step in buying successfully made');
        // this.updateTicketInLists(res);
      },
      (err: HttpErrorResponse)  =>{
        console.log(err.message);
      },
    );
  }

  updateTicketInLists(newTicket: Ticket){
    const indx = this.ticketsFiltered.findIndex( ticket => ticket.id === newTicket.id);
    this.ticketsFiltered.splice(indx, 1, newTicket);
    const indx2 = this.tickets.findIndex(ticket => ticket.id === newTicket.id);
    this.tickets.splice(indx2, 1, newTicket);

    console.log(this.tickets);
  }

}
