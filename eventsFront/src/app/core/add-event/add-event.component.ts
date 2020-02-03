import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { UserService } from 'src/app/services/user-service/user.service';
import { EventService } from '../../services/event-service/event.service';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { SectorService } from 'src/app/services/sector-service/sector.service';


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
  selectedHalls = [];
  selectedHallSectors;
  currentHall;
  selectedSectors = [];
  currentPlace;
  
  constructor(
    private eventService: EventService,
    private placeService: PlaceService,
    private hallService: HallService,
    private sectorService: SectorService,
    private formBuilder: FormBuilder,
  ) { 
    this.addEventForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      eventType: ['', Validators.required],
      place: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
      photos: '',
      videos: '', 
      ticketPrice: ['', Validators.required], 
      halls: this.formBuilder.array([]),
      sectors: this.formBuilder.array([]),
      priceList: this.formBuilder.array([]),
      mediaList: this.formBuilder.array([])
    });
  }

  ngOnInit() {
    this.placeService.getPlaces().subscribe(places => {
      if (places) {
        this.places = places;
      }
    });
  }

  get form() {
    return this.addEventForm.controls;
  }

  onSubmit(addEventData) {
    this.submitted = true;
    if (this.addEventForm.invalid) {
      return;
    }

    this.eventService.save(addEventData, this.selectedSectors).subscribe(response => {
      console.log(response);
    });
    // this.upload();
  }

  upload() {
    if (this.selectedFiles) {
      this.currentFileUpload = this.selectedFiles.item(0);
    }
    this.eventService.uploadFile(this.currentFileUpload).subscribe((url: string ) => {
      console.log(url);
    });
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  renderHalls(event) {
    const placeID = event.value;
    this.currentPlace = placeID;
    this.hallService.getHalls(placeID).subscribe(selectedPlaceHalls => {
      if (selectedPlaceHalls) {
        this.selectedPlaceHalls = selectedPlaceHalls;
      }
    });
  }

  renderSectors(event) {
    const hallID = event.target.value;
    this.currentHall = hallID;

    let sectors = [];
    this.selectedHalls.forEach((hall) => {
      if (hall.id == hallID) {
        sectors = hall.sectors;
      }
    })

    this.selectedHallSectors = sectors;
  }

  isSectorSelected = (sectorID) => {
    const sectors: FormArray = this.addEventForm.get('sectors') as FormArray;
    return sectors.value.includes(sectorID.toString());
  }

  onHallsCheckboxChange(event, hall) {
    const halls: FormArray = this.addEventForm.get('halls') as FormArray;

    if (event.checked) {
      halls.push(new FormControl(hall.id));
      this.selectedHalls.push(hall);
    } else {
      halls.controls = halls.controls.filter((item: FormControl) => {
        return item.value !== hall.id;
      });

      this.selectedHalls = this.selectedHalls.filter(selectedHall => {
        return selectedHall.id !== hall.id;
      });
    }

    console.log(this.selectedHalls.length > 0);
  }

  onCheckboxChangeSector(e) {

    const sectors: FormArray = this.addEventForm.get('sectors') as FormArray;

    if (e.target.checked) {
      sectors.push(new FormControl(e.target.value));
      this.selectedSectors.push({
        id: e.target.value,
        hallID: this.currentHall
      });
    } else {
      let i = 0;
      sectors.controls.forEach((item: FormControl) => {
        if (item.value === e.target.value) {
          sectors.removeAt(i);
          return;
        }
        i++;
      });

      let j = 0;
      this.selectedSectors.forEach((sector) => {
        if (sector.id === e.target.value) {
          this.selectedSectors.splice(j, 1);
          return;
        }
        j++;
      });
    }

  }

}
