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
  selectedHalls;
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
      sectors: this.formBuilder.array([])
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
    this.submitted = true;
    if (this.addEventForm.invalid) {
      return;
    }

    this.eventService.save(addEventData, this.selectedSectors);
    //this.upload();
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

  onCheckboxChange(e) {
    const halls: FormArray = this.addEventForm.get('halls') as FormArray;
  
    if (e.target.checked) {
      halls.push(new FormControl(e.target.value));
    } else {
      let i: number = 0;
      halls.controls.forEach((item: FormControl) => {
        if (item.value == e.target.value) {
          halls.removeAt(i);
          return;
        }
        i++;
      });
    }

    const selectedHalls = [];
    halls.value.forEach((selectedHall) => {
      this.selectedPlaceHalls.forEach((hall) => {
        if (hall.id == selectedHall) {
          selectedHalls.push(hall);
        }
      })
    })

    this.selectedHalls = selectedHalls;
  }

  onCheckboxChangeSector(e) {

    const sectors: FormArray = this.addEventForm.get('sectors') as FormArray;
  
    if (e.target.checked) {
      sectors.push(new FormControl(e.target.value));
      this.selectedSectors.push({
        id: e.target.value,
        hallID: this.currentHall
      })
    } else {
      let i: number = 0;
      sectors.controls.forEach((item: FormControl) => {
        if (item.value == e.target.value) {
          sectors.removeAt(i);
          return;
        }
        i++;
      });

      let j: number = 0;
      this.selectedSectors.forEach((sector) => {
        if (sector.id == e.target.value) {
          this.selectedSectors.splice(j, 1);
          return;
        }
        j++;
      });
    }

  }
}
