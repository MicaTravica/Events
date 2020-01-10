import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user-service/user.service';
import { EventService } from '../../services/event-service/event.service';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { HallService } from 'src/app/services/hall-service/hall.service';


@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.scss']
})
export class AddEventComponent implements OnInit {
  addEventForm;
  submitted;
  selectedFiles: FileList;
  currentFileUpload: File;
  places;
  selectedPlaceHalls;
  
  constructor(
    private eventService: EventService,
    private placeService: PlaceService,
    private hallService: HallService,
    private formBuilder: FormBuilder,
  ) { 
    this.addEventForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      type: ['', Validators.required],
      place: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
      photos: '',
      videos: '', 
      ticketPrice: ['', Validators.required], 
      halls: []
    });
  }

  ngOnInit() {
    this.placeService.getPlaces().subscribe(places => {
      if (places)
      this.places = places;
    });
  }

  get form() { 
    return this.addEventForm.controls; 
  }

  onSubmit(addEventData) {

    console.log(this.addEventForm.controls.hal);

    this.addEventForm.controls.halls["controls"].map((hall, i) => {
      console.log(hall.checked);
      console.log(hall.value);
    });

    this.submitted = true;
    if (this.addEventForm.invalid) {
      return;
    }

    this.eventService.save(addEventData);
    this.upload();
  }

  upload() {
    if (this.selectedFiles) this.currentFileUpload = this.selectedFiles.item(0);
    this.eventService.uploadFile(this.currentFileUpload).subscribe(() => {
      this.selectedFiles = undefined;
    }
    );
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  renderHalls(event) {
    const placeID = event.target.value;
    this.hallService.getHalls(placeID).subscribe(selectedPlaceHalls => {
      if (selectedPlaceHalls)
      this.selectedPlaceHalls = selectedPlaceHalls;
    });
  }
}
