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
  sectorChoosen = false;
  sector: any = {};
  tickets: number[] = [];

  ticketMap: { [key: string]: Ticket[] } = {};   // map key=date, value list of tickets
  dates = [];
  fromDate: Date = new Date(2000, 0, 1);
  toDate: Date = new Date(2000, 0, 1);

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
          this.fromDate = new Date(this.event.fromDate);
          this.toDate = new Date(this.event.toDate);
          this.event.fromDate = this.fromDate;
          this.event.toDate = this.toDate;
        }
        , (error: HttpErrorResponse) => {
          console.log(error.message);
        },
      );
    }
  }

  chooseHall(hallId: number) {
    if (hallId !== -1) {
      this.hallChoosen = true;
      this.sectorChoosen = false;
      this.hall = this.event.halls.find(h => h.id === hallId);
    } else {
      this.hallChoosen = false;
      this.sectorChoosen = false;
      this.hall = {};
    }
  }

  // treba da se proveri sta je zauzeto u sektoru..
  chooseSector(sectorId: number) {
    if (sectorId !== -1) {
      this.sector = this.hall.sectors.find(s => s.id === sectorId);
      this.sectorChoosen = true;
    } else {
      this.sector = {};
      this.sectorChoosen = false;
    }
  }

  // ticket vec postoji da li postoji nacin da ih dobavim
  reserveTickets() {
    if (this.tickets.length >= 1) {
      this.ticketService.makeReservation(this.tickets).subscribe(
        (res: any) => {
          this.tickets = [];
          this.ticketMap = {};
          this.dates = [];
          this.toastr.success('reservation successfully made');
        },
        (err: HttpErrorResponse)  => {
          console.log(err.message);
        }
      );
    } else {
      console.log('Choose at least one ticket');
    }
  }

  buyTickets() {
    if (this.tickets.length >= 1) {
      this.ticketService.startBuyingProcess(this.tickets).subscribe(
        (res: {status: string, redirect_url: string }) => {
          if (res.status === 'success') {
            this.ticketService.setTicketIdsToLocalStorage(this.tickets);
            this.ticketService.redirectPayPal(res.redirect_url);
            this.tickets = [];
            this.ticketMap = {};
            this.dates = [];
          }
        },
        (err: HttpErrorResponse)  => {
          console.log(err.message);
        },
      );
    }
  }

  search() {
    this.ticketMap = {};
    this.dates = [];
    this.adjustFromAndToDatesForDataPickers();
    const payload = {fromDate: this.fromDate, toDate: this.toDate,
                    sectorId: this.sector.id, eventId: this.event.id};
    this.ticketService.getTicketsByDateAndHallAndSector(payload).subscribe(
        (tickets: Ticket[]) => {
          tickets.forEach( (t: Ticket) => {
              if (!this.ticketMap[t.fromDate]) {
                this.ticketMap[t.fromDate] = [];
                this.dates.push(t.fromDate);
              }
              this.ticketMap[t.fromDate].push(t);
          });
        },
        (err: HttpErrorResponse)  => {
          console.log(err.message);
        },
        () => {
          console.log(this.ticketMap);
        });
  }

  adjustFromAndToDatesForDataPickers() {
    this.fromDate.setHours(this.event.fromDate.getHours());
    this.fromDate.setMinutes(this.event.fromDate.getMinutes());

    this.toDate.setHours(this.event.toDate.getHours());
    this.toDate.setMinutes(this.event.toDate.getMinutes());
  }

  selectedTicket(ticket: Ticket) {
    const indx = this.tickets.findIndex(t => t === ticket.id);
    if (indx === -1 && ticket.ticketState === 'AVAILABLE') {
      this.tickets.push(ticket.id);
    }
  }

  canceled() {
    this.tickets = [];
  }

  setClass(ticket: Ticket): string {
    let retVal = 'available-ticket';
    if (ticket.ticketState !== 'AVAILABLE') {
      retVal = 'reserved-ticket';
    } else if (this.tickets.findIndex(t1 => t1 === ticket.id) !== -1) {
      retVal = 'selected-ticket';
    }
    return retVal;
  }

}
