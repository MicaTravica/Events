import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { Place } from 'src/app/models/place-model/place.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';

@Component({
  selector: 'app-place-details',
  templateUrl: './place-details.component.html',
  styleUrls: ['./place-details.component.scss']
})
export class PlaceDetailsComponent implements OnInit {

  place: Place;
  role = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private placeService: PlaceService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.role = this.authService.getUserRole();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.placeService.getPlace(id).subscribe(
        (data: Place) => {
          this.place = data;
        }
      );
    }
  }

  updatePlace() {
    this.router.navigate(['/updatePlace/' + this.place.id]);
  }

  addHall() {
    this.router.navigate(['/addHall/' + this.place.id]);
  }

  cancel() {
    this.router.navigate(['/places']);
  }
}
