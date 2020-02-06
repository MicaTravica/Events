import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from '../../services/event-service/event.service';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { HttpErrorResponse } from '@angular/common/http';
import { TicketService } from 'src/app/services/ticket-service/ticket.service';
import { Ticket } from 'src/app/models/ticket-model/ticket.model';
import { ToastrService } from 'ngx-toastr';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { ticketNumberValidator} from 'src/app/util/form-validators';
import { MatTabChangeEvent } from '@angular/material';

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

  selectedParter = false;

  ticketMap: { [key: string]: Ticket[] } = {};   // map key=date, value list of tickets
  dates = [];
  fromDate: Date = new Date(2000, 0, 1);
  toDate: Date = new Date(2000, 0, 1);

  parterMap: { [key: string]: number} = {};

  parterForms: { [key: string]: FormGroup} = {};
  desiredNumbers: { [key: string]: FormControl} = {};
  availableNumbers: { [key: string]: FormControl} = {};
  parterTickets: number[];

  dataReady = false;
  showSelectParterSeats = false;


  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private ticketService: TicketService,
    private toastr: ToastrService,
    private fb: FormBuilder) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.parterTickets = [];
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
    const retVal: number[] = [];
    if (this.tickets.length >= 1) {
      this.tickets.forEach(t => {
        retVal.push(t);
      });
    }
    if (this.parterTickets.length >= 1) {
      this.parterTickets.forEach(t => {
        retVal.push(t);
      });
    }
    if (retVal.length >= 1) {
      this.ticketService.makeReservation(retVal).subscribe(
        (res: any) => {
          this.parterTickets = [];
          this.tickets = [];
          this.ticketMap = {};
          this.dates = [];
          this.toastr.success('reservation successfully made');
        },
        (err: HttpErrorResponse)  => {
          this.toastr.error(err.message);
        }
      );
    } else if (this.parterTickets.length >= 1) {
      this.toastr.warning('Choose at least one ticket');
    }
  }

  buyTickets() {
    const retVal: number[] = [];
    if (this.tickets.length >= 1) {
      this.tickets.forEach(t => {
        retVal.push(t);
      });
    }
    if (this.parterTickets.length >= 1) {
      this.parterTickets.forEach(t => {
        retVal.push(t);
      });
    }
    if (retVal.length >= 1) {
      this.ticketService.startBuyingProcess(retVal).subscribe(
        (res: {status: string, redirect_url: string }) => {
          if (res.status === 'success') {
            this.ticketService.setTicketIdsToLocalStorage(retVal);
            this.ticketService.redirectPayPal(res.redirect_url);
            this.tickets = [];
            this.ticketMap = {};
            this.parterTickets = [];
            this.dates = [];
          }
        },
        (err: HttpErrorResponse)  => {
          this.toastr.warning(err.message);
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
              this.selectedParter =  t.seatRow === -1 ? true : false;
              this.ticketMap[t.fromDate].push(t);
              this.ticketMap[t.fromDate].sort(
                (ticket1, ticket2) => {
                  if (ticket1.seatRow > ticket2.seatRow) {return 1; }
                  if (ticket1.seatRow < ticket2.seatRow) {return -1; }

                  if (ticket1.seatColumn > ticket2.seatColumn) {return 1; }
                  if (ticket1.seatColumn < ticket2.seatColumn) {return -1; }
                });
          });
        },
        (err: HttpErrorResponse)  => {
          console.log(err.message);
        },
        () => {
          if (this.selectedParter) {
            this.parterForms = {};
            this.availableNumbers = {};
            this.desiredNumbers = {};
            this.dates.forEach(d => {
              // za svaki datum da znas koliko ima ukupno
              // koliko ima slobodno
              const availableTickets = this.ticketMap[d].filter(t => t.ticketState === 'AVAILABLE').length;
              this.parterMap[d] = availableTickets;

              // napravi forme za svaki date i podesi koliko moze da rezervise..
              this.availableNumbers[d] =
                 new FormControl({value: this.parterMap[d], disabled : true}, []);
              this.desiredNumbers[d] = new FormControl(0, [Validators.required, Validators.min(0)]);
              this.parterForms[d] = this.fb.group({
                desiredNumber: this.desiredNumbers[d],
                availableNumber: this.availableNumbers[d]
            },
              {validators: [ticketNumberValidator]
            });
          });
          }
          this.dataReady = true;
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
    } else {
      this.tickets.splice(indx, 1);
    }
  }

  canceled() {
    this.tickets = [];
    this.parterTickets = [];
    this.dates.forEach(date => {
      this.desiredNumbers[date].setValue(0);
    });
    this.showSelectParterSeats = false;
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


  findDesiredNumbers(d: string): FormControl {
    return this.desiredNumbers[d] as FormControl;
  }
  findAvailableNumbers(d: string): FormControl {
    return this.availableNumbers[d] as FormControl;
  }

  setParterFlags() {
    let val = 0;
    this.dates.forEach(date => {
      if (this.parterForms[date].valid) {
        val += this.desiredNumbers[date].value as number;
      }
    });
    if (val > 0 ) {
      this.showSelectParterSeats = true;
    } else {
      this.showSelectParterSeats = false;
    }
  }

  selectParterSeats() {
    this.parterTickets = [];
    this.dates.forEach(date => {
      if (this.parterForms[date].valid) {
        let i = 0;
        const n  = this.desiredNumbers[date].value as number;
        while (i < n) {
          this.parterTickets.push(this.ticketMap[date][i].id);
          i++;
        }
      } else {
        this.toastr.warning('For date:' + date + 'invalid input');
      }
    });
  }


}
