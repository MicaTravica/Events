import { Component, OnInit } from '@angular/core';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { ActivatedRoute, Router } from '@angular/router';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { Place } from 'src/app/models/place-model/place.model';

@Component({
  selector: 'app-hall-details',
  templateUrl: './hall-details.component.html',
  styleUrls: ['./hall-details.component.scss']
})
export class HallDetailsComponent implements OnInit {

  place: Place;
  hall: Hall;
  role = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hallService: HallService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.role = this.authService.getUserRole();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.hallService.getHall(id).subscribe(
        (data: Hall) => {
          this.hall = data;
          this.place = data.place;
        }
      );
    }
  }

  updateHall() {
    this.router.navigate(['/updateHall/' + this.hall.id]);
  }

  addSector() {
    this.router.navigate(['/addSector/' + this.hall.id]);
  }

  cancel() {
    this.router.navigate(['/place/' + this.place.id]);
  }
}
