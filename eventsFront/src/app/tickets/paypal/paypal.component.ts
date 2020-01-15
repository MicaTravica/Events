import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketService } from 'src/app/services/ticket-service/ticket.service';

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

      if (!paymentId || !PayerID || !token) {
        this.state = 'something went wrong go back to home page';
        return;
      }

    // kako ovde da dobijem id od tiketa?? i sta cemo ako bude vise tiketa odjednom??
      this.ticketService.finishBuyingProcess(paymentId, token, PayerID).subscribe(
        res => {
          console.log(res);
          this.state = 'successfully bought ticket';
          this.ticketService.removeTicketIdsFromLocalStorage();
          this.router.navigate(['/reservations']);
        },
        err => {
          console.log(err.message);
          this.state = 'something went wrong go back to home page';
          this.ticketService.removeTicketIdsFromLocalStorage();
        }
      );
    });
  }

}
