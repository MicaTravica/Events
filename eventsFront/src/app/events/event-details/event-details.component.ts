import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from 'src/app/services/event-service/event.service';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { MediaService } from 'src/app/services/media-service/media.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { AngularFireStorage } from 'angularfire2/storage';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent implements OnInit {

  event = new EventEntity();
  activeImg = 0;
  role = '';
  wait = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService,
    private mediaService: MediaService,
    private authService: AuthService,
    private afStorage: AngularFireStorage
  ) { }

  ngOnInit() {
    this.role = this.authService.getUserRole();
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
    this.router.navigate(['/reservation/' + this.event.id]);
  }

  previous() {
    this.activeImg -= 1;
  }

  next() {
    this.activeImg += 1;
  }

  upload(event: any): void {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      const path = `events/${Date.now()}_${file.name}`;
      const task = this.afStorage.upload(path, file);
      const ref = this.afStorage.ref(path);
      this.wait = true;
      task.snapshotChanges().pipe(
        finalize(() => {
          ref.getDownloadURL().subscribe((value: string) => {
            const media = { id: null, path: value, eventId: this.event.id };
            this.mediaService.createMedia(media).subscribe(
              (data: any) => {
                this.event.mediaList.push(data);
                this.wait = false;
              });
          });
        })
      ).subscribe();
    }
  }
}
