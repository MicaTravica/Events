import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { EventSearch } from 'src/app/models/event-search-model/event-search.model';
import { EventState } from 'src/app/models/event-model/event-state.enum';
import { EventType } from 'src/app/models/event-model/event-type.enum';
import { PlaceService } from 'src/app/services/place-service/place.service';

@Component({
  selector: 'app-event-search',
  templateUrl: './event-search.component.html',
  styleUrls: ['./event-search.component.scss']
})
export class EventSearchComponent implements OnInit {

  eventSearch = new EventSearch(0, 8, 'fromDate', '', null, null, null, null, null);
  eventStates = [null, EventState.AVAILABLE, EventState.NOT_AVAILABLE, EventState.SOLD_OUT, EventState.FINISHED, EventState.CANCELED];
  eventTypes = [null, EventType.SPORT, EventType.BALLET, EventType.CONCERT, EventType.DRAMA, EventType.OPERA];
  places = [{ id: null, name: null }];
  sorts = [{ id: 'name', name: 'Name' }, { id: 'fromDate', name: 'From date' }, { id: 'toDate', name: 'To date' }];
  sizes = [8, 16, 32];
  @Output() public searchParams = new EventEmitter<EventSearch>();

  constructor(
    private placeService: PlaceService
  ) {
    this.placeService.getPlaces().subscribe(
      (data: any[]) => {
        this.places = this.places.concat(data);
      }
    );
  }

  ngOnInit() {
  }

  search() {
    this.searchParams.emit(this.eventSearch);
  }
}
