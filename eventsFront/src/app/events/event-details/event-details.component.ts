import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  activeImg = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService,
    private mediaService: MediaService
  ) { }

  ngOnInit() {
    // tslint:disable-next-line: no-string-literal
    const id = this.route.snapshot.params['id'];
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
      this.router.navigate(['/reservation/' + this.event.id]);
  }

  previous() {
    this.activeImg -= 1;
  }

  next() {
    this.activeImg += 1;
  }
}
