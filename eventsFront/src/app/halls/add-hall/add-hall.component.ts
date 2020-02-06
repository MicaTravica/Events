import { Component, OnInit, Input } from '@angular/core';
import { Place } from 'src/app/models/place-model/place.model';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-add-hall',
    templateUrl: './add-hall.component.html',
    styleUrls: ['./add-hall.component.scss']
  })
export class AddHallComponent implements OnInit {

  place: Place;
  halls: Hall[];
  checked = [[false]];
  @Input() newPlace: Place;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private placeService: PlaceService,
    private hallService: HallService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    if (this.newPlace) {
      this.place = this.newPlace;
      this.halls = [new Hall(null, '', this.place, [new Sector(null, '', null, null, null, null)])];
    } else {
      const id = this.route.snapshot.paramMap.get('id');
      if (id) {
        this.placeService.getPlace(id).subscribe(
          (data: Place) => {
            this.place = data;
            this.halls = [new Hall(null, '', this.place, [new Sector(null, '', null, null, null, null)])];
          }
        );
      }
    }
  }

  addOneMoreSector(inx: number) {
    this.checked[inx].push(false);
    this.halls[inx].sectors.push(new Sector(null, '', null, null, null, null));
  }

  addOneMoreHall() {
    this.checked.push([false]);
    this.halls.push(new Hall(null, '', this.place, [new Sector(null, '', null, null, null, null)]));
  }

  cancel() {
    this.router.navigate(['/place/' + this.place.id]);
  }

  add() {
   // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.halls.length; i++) {
      this.hallService.add(this.halls[i]).subscribe(
      (data: Hall) => {
        this.toastr.success('Successful add!');
        this.router.navigate(['/hall/' + data.id]);
      }
      );
    }
  }

}
