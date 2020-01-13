import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from 'src/app/services/event-service/event.service';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { MediaService } from 'src/app/services/media-service/media.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent implements OnInit {

  event = new EventEntity();

  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private mediaService: MediaService
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.eventService.getEvent(id).subscribe(
        (data: EventEntity) => {
          this.event = data;
        }
        , (error: HttpErrorResponse) => {
        },
        () => {
          this.mediaService.getMediaForEvent(id).subscribe(
          (data: any) => {
            this.event.mediaList = data;
          }
      );
      });
    }
  }

  buyReserve() {
    console.log('tickets');
  }
}
