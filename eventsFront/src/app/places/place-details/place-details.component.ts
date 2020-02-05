import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { Place } from 'src/app/models/place-model/place.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';

@Component({
  selector: 'app-place-details',
  templateUrl: './place-details.component.html',
  styleUrls: ['./place-details.component.scss']
})
export class PlaceDetailsComponent implements OnInit {

  place: Place;
  halls: Hall[];
  hall: Hall;
  role = '';
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private placeService: PlaceService,
    private authService: AuthService
  ) { }

updatePlace() {
  this.router.navigate(['/updatePlace/' + this.place.id]);
}

addHall() {
  this.router.navigate(['/addHall/' + this.place.id]);
}

showDetails() {
  this.router.navigate(['/hall/' + this.hall.id]);
}

cancel() {
  this.router.navigate(['/places']);
}

ngOnInit() {
  this.role = this.authService.getUserRole();
  const id = this.route.snapshot.paramMap.get('id');
  if (id) {
    this.placeService.getPlace(id).subscribe(
      (data: Place) => {
      this.place = data;
      this.halls = data.halls;
    }
    , (error: HttpErrorResponse) => {
   },
    );
  }
}
}
