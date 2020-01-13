import { Component, OnInit, Input } from '@angular/core';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event-list-item',
  templateUrl: './event-list-item.component.html',
  styleUrls: ['./event-list-item.component.scss']
})
export class EventListItemComponent implements OnInit {

  @Input() event: EventEntity;

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  showDetails() {
    this.router.navigate(['/event/' + this.event.id]);
  }

  buyReserve() {
    console.log('tickets');
  }
}
