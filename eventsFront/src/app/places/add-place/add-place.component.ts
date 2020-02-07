import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { Place } from 'src/app/models/place-model/place.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-add-event',
  templateUrl: './add-place.component.html',
  styleUrls: ['./add-place.component.scss']
})
export class AddPlaceComponent implements OnInit {

  place = new Place(null, '', '', 0, 0, []);

  constructor(
    private placeService: PlaceService,
    private router: Router,
    private toastr: ToastrService
  ) { }

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
