import { Component, OnInit } from '@angular/core';
import { Place } from 'src/app/models/place-model/place.model';
import { ActivatedRoute, Router } from '@angular/router';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    // tslint:disable-next-line: component-selector
  selector: 'update-place',
  templateUrl: './update-place.component.html',
  styleUrls: ['./update-place.component.scss']
})
export class UpdatePlaceComponent implements OnInit {

  place: Place;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private placeService: PlaceService,
    private toastr: ToastrService
  ) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.placeService.getPlace(id).subscribe(
        (data: Place) => {
          this.place = data;
        }
      );
    }
  }

  cancel() {
    this.router.navigate(['/place/' + this.place.id]);
  }

  update() {
    this.placeService.update(this.place).subscribe(
      (data: any) => {
        this.place = data;
        this.toastr.success('Successful update!');
        this.router.navigate(['/place/' + this.place.id]);
      }
    );
    }
  }
