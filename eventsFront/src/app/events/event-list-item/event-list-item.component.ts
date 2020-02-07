import { Component, OnInit, Input } from '@angular/core';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth-service/auth.service';

@Component({
  selector: 'app-event-list-item',
  templateUrl: './event-list-item.component.html',
  styleUrls: ['./event-list-item.component.scss']
})
export class EventListItemComponent implements OnInit {

  role = '';
  @Input() event: EventEntity;

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.role = this.authService.getUserRole();
  }

  showDetails() {
    this.router.navigate(['/event/' + this.event.id]);
  }

  buyReserve() {
    this.router.navigate(['/reservation/' + this.event.id]);
  }
}
