import { Component, OnInit } from '@angular/core';
import { PlaceService } from 'src/app/services/place-service/place.service';

@Component({
  selector: 'app-places-list',
  templateUrl: './places-list.component.html',
  styleUrls: ['./places-list.component.scss']
})
export class PlacesListComponent implements OnInit {

  places: any[]; // zamenit sa place kad neko napravi
  // eventSearch = new EventSearch(0, 8, '', true, '', null, null, null, null, null);
  totalElements = 0;
  name = '';
  numOfPage = 0;
  size = 8;
  sizes = [8, 16, 32];

  constructor(
    private placeService: PlaceService
  ) {
    this.search();
  }

  ngOnInit() {
  }

  search() {
    this.placeService.searchPlaces(this.name, this.numOfPage, this.size).subscribe(
      (data: Map<string, object>) => {
        // tslint:disable-next-line: no-string-literal
        this.places = data['content'];
        // tslint:disable-next-line: no-string-literal
        this.totalElements = data['totalElements'];
        // tslint:disable-next-line: no-string-literal
        this.size = data['size'];
      }
    );
  }

  doSearch() {
    this.numOfPage = 0;
    this.totalElements = 0;
    this.search();
  }

  pageChanged(num: number) {
    this.numOfPage = num - 1;
    this.search();
  }

  showDetails(id: number) {
    console.log('place page' + id);
  }
}
