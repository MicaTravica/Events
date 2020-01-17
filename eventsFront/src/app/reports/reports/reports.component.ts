import { Component, OnInit } from '@angular/core';
import { PlaceService } from 'src/app/services/place-service/place.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {

  places = [];
  type1 = ['Profit', 'Attendance'];
  type2 = ['Time', 'Event'];
  id: number;
  about: string;
  by: string;

  constructor(
    private placeService: PlaceService
  ) { }

  ngOnInit() {
    this.placeService.getPlaces().subscribe(
      (data: any[]) => { // zameni sa place
        this.places = data;
      }
    );
  }

}
