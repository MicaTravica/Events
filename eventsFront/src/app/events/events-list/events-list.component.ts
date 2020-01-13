import { Component, OnInit } from '@angular/core';
import { EventService } from 'src/app/services/event-service/event.service';
import { EventSearch } from 'src/app/models/event-search-model/event-search.model';
import { EventEntity } from 'src/app/models/event-model/event.model';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.scss']
})
export class EventsListComponent implements OnInit {
  events: EventEntity[];

  constructor(
    private eventService: EventService
  ) { }

  ngOnInit() {
    const params = new EventSearch(0, 10, '', '', null, null, null, null, null);
    this.eventService.search(params).subscribe(
      (data: Map<string, object>) => {
        // tslint:disable-next-line: no-string-literal
        this.events = data['content'];
      }
    );
  }
}
