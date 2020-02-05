import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UserService } from 'src/app/services/user-service/user.service';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { Place } from 'src/app/models/place-model/place.model';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { Observable } from 'rxjs';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-add-event',
  templateUrl: './add-place.component.html',
  styleUrls: ['./add-place.component.scss']
})
export class AddPlaceComponent implements OnInit {
  place = new Place(null, '', '', 0, 0, []);
  halls = [new Hall(1, '', this.place, [])];
  h = new Hall(1, '', this.place, []);
  addPlace: FormGroup;

  @Output() public newPlace = new EventEmitter<Place>();

  constructor(
    private placeService: PlaceService,
    private router: Router,
    private toastr: ToastrService,
    private formBuilder: FormBuilder

  ) {
    this.addPlace = this.formBuilder.group({
      name: ['', Validators.required],
      address: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  cancel() {
    this.router.navigate(['/places']);
  }

  add() {
    this.placeService.add(this.place).subscribe(
      (data: any) => {
        this.toastr.success('Successful add!');
        this.place = data;
      }
    );
  }




}
