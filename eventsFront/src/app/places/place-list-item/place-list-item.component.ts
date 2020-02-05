import { Component, OnInit, Input } from '@angular/core';
import { Place } from 'src/app/models/place-model/place.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-place-list-item',
  templateUrl: './place-list-item.component.html',
  styleUrls: ['./place-list-item.component.scss']
})
export class PlaceListItemComponent implements OnInit {

  @Input() place: Place;

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  showDetails() {
    this.router.navigate(['/place/' + this.place.id]);
  }
}
