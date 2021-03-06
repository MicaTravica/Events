import { Component, OnInit } from '@angular/core';
import { EventService } from 'src/app/services/event-service/event.service';
import { EventSearch } from 'src/app/models/event-search-model/event-search.model';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { HttpErrorResponse } from '@angular/common/http';
import { MediaService } from 'src/app/services/media-service/media.service';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.scss']
})
export class EventsListComponent implements OnInit {

  events: EventEntity[];
  eventSearch = new EventSearch(0, 8, '', true, '', null, null, null, null, null);
  totalElements = 0;
  size = 8;

  constructor(
    private eventService: EventService,
    private mediaService: MediaService
  ) { }

  ngOnInit() {
    this.search();
  }

  search() {
    this.eventService.search(this.eventSearch).subscribe(
      (data: Map<string, object>) => {
        // tslint:disable-next-line: no-string-literal
        this.events = data['content'];
        // tslint:disable-next-line: no-string-literal
        this.totalElements = data['totalElements'];
        // tslint:disable-next-line: no-string-literal
        this.size = data['size'];
      }, (error: HttpErrorResponse) => {

      },
      () => {
        for (const e of this.events) {
          this.mediaService.getOneMediaForEvent(e.id).subscribe(
            (data: any) => {
              if (data) {
                e.mediaList[0] = data;
              } else {
                e.mediaList = [];
              }
            }
          );
        }
      }
    );
  }

  doSearch(es: EventSearch) {
    this.eventSearch = es;
    this.size = es.sizeOfPage;
    this.totalElements = 0;
    this.search();
  }

  pageChanged(num: number) {
    this.eventSearch.numOfPage = num - 1;
    this.search();
  }
}
