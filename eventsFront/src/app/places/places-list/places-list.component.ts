import { Component, OnInit } from '@angular/core';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { Place } from 'src/app/models/place-model/place.model';
import { PlaceSearch } from 'src/app/models/place-search-model/place-search.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-places-list',
  templateUrl: './places-list.component.html',
  styleUrls: ['./places-list.component.scss']
})
export class PlacesListComponent implements OnInit {

  places: Place[];
  placeSearch = new PlaceSearch(0, 8, '', true, '', '');
  totalElements = 0;
  size = 8;

  constructor(
    private placeService: PlaceService,
    ) { }

  ngOnInit() {
    this.search();
  }

  search() {
    this.placeService.search(this.placeSearch).subscribe(
      (data: Map<string, object>) => {
        // tslint:disable-next-line: no-string-literal
        this.places = data['content'];
        // tslint:disable-next-line: no-string-literal
        this.totalElements = data['totalElements'];
        // tslint:disable-next-line: no-string-literal
        this.size = data['size'];
      }, (error: HttpErrorResponse) => {

      }
    );
  }

  doSearch(es: PlaceSearch) {
    this.placeSearch = es;
    this.size = es.sizeOfPage;
    this.totalElements = 0;
    this.search();
  }

  pageChanged(num: number) {
    this.placeSearch.numOfPage = num - 1;
    this.search();
  }
}
