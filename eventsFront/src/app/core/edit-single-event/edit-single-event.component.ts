import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from 'src/app/services/event-service/event.service';
import { MediaService } from 'src/app/services/media-service/media.service';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-edit-single-event',
  templateUrl: './edit-single-event.component.html',
  styleUrls: ['./edit-single-event.component.scss']
})
export class EditSingleEventComponent implements OnInit {

  event = new EventEntity();
  activeImg = 0;

  editedId;

  addEventForm;
  submitted;
  selectedFiles = [] as any;
  currentFileUpload: File;
  places;
  selectedPlaceHalls;
  selectedHalls = [];
  selectedHallSectors = [];
  currentHall;
  selectedSectors = [];
  currentPlace;
  selectedMedia = [];

  selectedType;
  selectedStartDate;
  selectedEndDate;

  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private mediaService: MediaService,
    private formBuilder: FormBuilder
  ) {
    this.addEventForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      eventType: ['', Validators.required],
      place: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
      ticketPrice: ['', Validators.required],
      halls: this.formBuilder.array([]),
      currentHall: [],
      sectors: this.formBuilder.array([]),
      priceList: this.formBuilder.array([]),
      mediaList: this.formBuilder.array([])
    });
   }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.eventService.getEvent(id).subscribe(
        (data: EventEntity) => {
          this.event = data;
          this.selectedType = this.selectedEventType(data.eventType);
          this.selectedStartDate = new Date(data.fromDate);
          this.selectedEndDate = new Date(data.toDate); // why not shown?
          this.editedId = data.id;
          console.log(data);
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

  onSubmit(addEventData) {
    // connect to api/event (PUT) -> same body as for add but with ID passed
    console.log(addEventData, this.editedId);
  }

  get form() {
    return this.addEventForm.controls;
  }

  selectedEventType(eventType) {
    switch (eventType) {
      case 'SPORT': return '1';
      case 'DRAMA': return '2';
      case 'OPERA': return '3';
    }
  }

}
