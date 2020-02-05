import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { UserService } from 'src/app/services/user-service/user.service';
import { EventService } from '../../services/event-service/event.service';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { SectorService } from 'src/app/services/sector-service/sector.service';
import { DomSanitizer } from '@angular/platform-browser'
@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.scss']
})
export class AddEventComponent implements OnInit {
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

  constructor(
    private eventService: EventService,
    private placeService: PlaceService,
    private hallService: HallService,
    private formBuilder: FormBuilder,
    private sanitizer: DomSanitizer
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

    if (this.selectedFiles) {
      this.currentFileUpload = this.selectedFiles[0];
    }
    this.eventService.uploadFile(this.currentFileUpload).subscribe((url: string ) => {
      this.selectedMedia.push({
        path: url
      });
      this.saveEvent(addEventData);
    });
  }

  saveEvent(addEventData) {
    this.eventService.save(addEventData, this.selectedSectors, this.selectedMedia).subscribe(response => {
      console.log(response);
    });
  }

  selectFile(event) {
    const [file] = event.target.files;
    this.selectedFiles.unshift(file);
  }

  getLastFile() {
    return this.selectedFiles[this.selectedFiles.length - 1];
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
    const hallID = event.value;
    this.currentHall = hallID;

    let sectors = [];
    this.selectedHalls.forEach((hall) => {
      if (hall.id === Number(hallID)) {
        sectors = hall.sectors;
      }
    });

    this.selectedHallSectors = sectors;
  }

  isSectorSelected = (sectorID) => {
    const selectedSectorsIds = this.selectedSectors.map(sector => sector.id);
    return selectedSectorsIds.includes(sectorID);
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
  }

  onSectorCheckboxChange(event, sector) {
    const sectors: FormArray = this.addEventForm.get('sectors') as FormArray;

    if (event.checked) {
      sectors.push(new FormControl(sector.id));
      this.selectedSectors.push({
        id: sector.id,
        hallID: this.currentHall
      });
    } else {
      sectors.controls = sectors.controls.filter((item: FormControl) => {
        return item.value !== sector.id;
      });

      this.selectedSectors = this.selectedSectors.filter(selectedSector => {
        return selectedSector.id !== sector.id;
      });
    }
  }

}
