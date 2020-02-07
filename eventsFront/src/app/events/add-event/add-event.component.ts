import { Component, OnInit } from '@angular/core';
import { EventService } from '../../services/event-service/event.service';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { Place } from 'src/app/models/place-model/place.model';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { EventType } from 'src/app/models/event-model/event-type.enum';
import { EventState } from 'src/app/models/event-model/event-state.enum';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { MatSelectChange, MatCheckboxChange } from '@angular/material';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { PriceList } from 'src/app/models/price-list-model/price-list.model';
import { ToastrService } from 'ngx-toastr';
import { AngularFireStorage } from 'angularfire2/storage';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.scss']
})
export class AddEventComponent implements OnInit {

  eventTypes = [EventType.SPORT, EventType.BALLET, EventType.CONCERT, EventType.DRAMA, EventType.OPERA];
  eventStates = [EventState.AVAILABLE, EventState.NOT_AVAILABLE];
  event: EventEntity = new EventEntity(null, null, null, null, null, EventState.AVAILABLE, EventType.SPORT, [], [], []);
  selectedFiles = [] as any;
  currentFileUpload: File;
  places: Place[];
  selectedPlaceHalls: Hall[];
  selectedHalls = [];
  selectedHallSectors = [];
  currentHall: number;
  selectedSectors = [];
  currentPlace: number;
  selectedMedia = [];
  minDate = new Date();
  hours = (new Array(24)).fill(undefined).map((_, i) => i);
  minutes = (new Array(60)).fill(undefined).map((_, i) => i);
  time = (new Array(4)).fill(0);
  wait = false;

  constructor(
    private eventService: EventService,
    private placeService: PlaceService,
    private hallService: HallService,
    private toastr: ToastrService,
    private afStorage: AngularFireStorage
  ) { }

  ngOnInit() {
    this.placeService.getPlaces().subscribe(
      (places: Place[]) => {
      if (places) {
        this.places = places;
      }
    });
  }

  onSubmit() {
    this.wait = true;
    this.event.fromDate.setHours(this.time[0]);
    this.event.fromDate.setMinutes(this.time[1]);
    this.event.toDate.setHours(this.time[2]);
    this.event.toDate.setMinutes(this.time[3]);
    this.eventService.save(this.event).subscribe(
      response => {
        this.toastr.success('You add event!');
        this.wait = false;
      }, () => {
        this.wait = false;
    });
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
            const media = { id: null, path: value, eventId: null };
            this.event.mediaList.push(media);
            this.selectedFiles.push({ name: file.name, url: value });
            this.wait = false;
          });
        })
      ).subscribe();
    }
  }

  getLastFile() {
    return this.selectedFiles[this.selectedFiles.length - 1];
  }

  renderHalls(event: MatSelectChange) {
    const placeID = event.value;
    this.currentPlace = placeID;
    this.hallService.getHalls(placeID).subscribe(
      (selectedPlaceHalls: Hall[]) => {
        if (selectedPlaceHalls) {
          this.selectedPlaceHalls = selectedPlaceHalls;
        }
      });
  }

  renderSectors(event: MatSelectChange) {
    const hallID = event.value;
    this.currentHall = hallID;

    let sectors = [];
    this.selectedHalls.forEach((hall: Hall) => {
      if (hall.id === Number(hallID)) {
        sectors = hall.sectors;
      }
    });

    this.selectedHallSectors = sectors;
  }

  onHallsCheckboxChange(event: MatCheckboxChange, hall: Hall) {
    if (event.checked) {
      this.event.halls.push(new Hall(hall.id, hall.name, hall.place, []));
      this.selectedHalls.push(hall);
    } else {
      this.event.halls = this.event.halls.filter((item: Hall) => {
        return item.id !== hall.id;
      });
      this.selectedHalls = this.selectedHalls.filter((item: Hall) => {
        return item.id !== hall.id;
      });
    }
  }

  isSectorSelected = (sectorID: number) => {
    return this.selectedSectors.includes(sectorID);
  }

  onSectorCheckboxChange(event: MatCheckboxChange, idx: number, sector: Sector) {
    if (event.checked) {
      sector.sectorCapacity = null;
      this.event.halls[idx].sectors.push(sector);
      this.event.priceList.push(new PriceList(null, null, null, sector.id));
      this.selectedSectors.push(sector.id);
    } else {
      this.event.halls[idx].sectors = this.event.halls[idx].sectors.filter((item: Sector) => {
        return item.id !== sector.id;
      });
      this.event.priceList = this.event.priceList.filter((item: PriceList) => {
        return item.sectorId !== sector.id;
      });
      this.selectedSectors = this.selectedSectors.filter((id: number) => {
        return id !== sector.id;
      });
    }
  }

  getPriceList(sectorID: number): number {
    for (const pl of this.event.priceList) {
      if (pl.sectorId === sectorID) {
        return pl.price;
      }
    }
    return 0;
  }

  setPriceList(event: any, sectorID: number) {
    for (const pl of this.event.priceList) {
      if (pl.sectorId === sectorID) {
        pl.price = event.srcElement.value;
      }
    }
  }

  sectorsChoosen(): boolean {
    for (const h of this.event.halls) {
      if (h.sectors.length < 1) {
        return true;
      }
    }
    return false;
  }

  remove(i: number) {
    const [path] = this.selectedFiles.splice(i, 1);
    this.event.mediaList.splice(i, 1);
    this.afStorage.storage.refFromURL(path.url).delete();
  }
}
