import { Component, OnInit } from '@angular/core';
import { Place } from 'src/app/models/place-model/place.model';
import { ActivatedRoute, Router } from '@angular/router';
import { PlaceService } from 'src/app/services/place-service/place.service';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

@Component({
    // tslint:disable-next-line: component-selector
  selector: 'update-place',
  templateUrl: './update-place.component.html',
  styleUrls: ['./update-place.component.scss']
})
export class UpdatePlaceComponent implements OnInit {

  place: Place;
  role = '';
  updatePlace: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private placeService: PlaceService,
    private authService: AuthService,
    private toastr: ToastrService,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.placeService.getPlace(id).subscribe(
        (data: Place) => {
          this.updatePlace = this.formBuilder.group({
            name: [data.name, Validators.required],
            address: [data.address, Validators.required]
          });
          this.place = data;
        }
        , (error: HttpErrorResponse) => {
        },
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
