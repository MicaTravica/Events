import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketService } from '../services/ticket-service/ticket.service';

@Component({
  selector: 'app-paypal',
  templateUrl: './paypal.component.html',
  styleUrls: ['./paypal.component.scss']
})
export class PaypalComponent implements OnInit {

  state = 'work in progress';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const paymentId = params['paymentId'];
      const token = params['token'];
      const PayerID = params['PayerID'];

      console.log(paymentId);
      console.log(PayerID);
      console.log(token);

    // kako ovde da dobijem id od tiketa?? i sta cemo ako bude vise tiketa odjednom??
      this.ticketService.finishBuyingProcess(1, paymentId, token, PayerID).subscribe(
        res => {
          console.log(res);
          this.state = 'successfully bought ticket';
        },
        err => {
          console.log(err.message);
          this.state = 'something went wrong';
        }
      );
    });
  }

}