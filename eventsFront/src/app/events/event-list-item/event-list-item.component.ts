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
  @Input() type: string;

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  showDetails() {
    this.router.navigate(['/event/' + this.event.id]);
  }

  buyReserve() {
    this.router.navigate(['/reservation/' + this.event.id]);
  }

  isMainType() {
    return this.type === 'mainList';
  }

  isEditType() {
    return this.type === 'editList';
  }

  showEditPage(event) {
    this.router.navigate(['/edit-event/' + event.id]);
  }
}
