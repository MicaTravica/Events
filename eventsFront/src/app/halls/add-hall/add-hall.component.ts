import { Component, OnInit } from '@angular/core';
import { Place } from 'src/app/models/place-model/place.model';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { SectorService } from 'src/app/services/sector-service/sector.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { FormControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-add-hall',
    templateUrl: './add-hall.component.html',
    styleUrls: ['./add-hall.component.scss']
  })
export class AddHallComponent implements OnInit {

  place: Place;
  role = '';
  halls = [new Hall(null, '', this.place, [new Sector(null, '', null, null, null, null)])];
  h = new Hall(null, '', this.place, [new Sector(null, '', null, null, null, null)]);
  disableSelect = new FormControl(false);
  addHall: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private placeService: PlaceService,
    private authService: AuthService,
    private hallService: HallService,
    private sectorService: SectorService,
    private toastr: ToastrService,
    private formBuilder: FormBuilder
  ) {
    this.addHall = this.formBuilder.group({
    name: ['', Validators.required],
    nameSector: ['', Validators.required],
    sectorRows: [Validators.required,  Validators.min(1)],
    sectorColumns: [Validators.required,  Validators.min(1)]
  });

  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.placeService.getPlace(id).subscribe(
        (data: Place) => {
          this.place = data;
        }
        , (error: HttpErrorResponse) => {
        },
        );
    }
  }

  addOneMoreSector(inx: number) {
    this.halls[inx].sectors.push(new Sector(null, '', null, null, null, null));
  }

  addOneMoreHall() {
    this.halls.push(new Hall(null, '', this.place, [new Sector(null, '', null, null, null, null)]));
  }

  cancel() {
    this.router.navigate(['/place/' + this.place.id]);
  }

  add() {
   // tslint:disable-next-line: prefer-for-of
    for ( let i = 0; i < this.halls.length; i++) {
      this.h = this.halls[i];
      this.h.place = this.place;
      this.hallService.add(this.h).subscribe(
      (data: any) => {
        this.h = data;
        this.toastr.success('Successful add!');
        this.router.navigate(['/place/' + this.place.id]);
      }
      );
    }
  }

}
