
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { Router } from '@angular/router';
import { PlaceSearch } from 'src/app/models/place-search-model/place-search.model';

@Component({
  selector: 'app-place-search',
  templateUrl: './place-search.component.html',
  styleUrls: ['./place-search.component.scss']
})
export class PlaceSearchComponent implements OnInit {

  placeSearch = new PlaceSearch(0, 8, 'name', true, '', '');
  sorts = [{ id: 'name', name: 'Name' }, {id: 'address', name: 'Address'}];
  orders = [{ id: true, name: 'Ascending' }, { id: false, name: 'Descending' }];
  sizes = [8, 16, 32];
  @Output() public searchParams = new EventEmitter<PlaceSearch>();

  ngOnInit() {
  }

  search() {
    this.placeSearch.numOfPage = 0;
    this.searchParams.emit(this.placeSearch);
  }
}
