import { Component, OnInit } from '@angular/core';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { SectorService } from 'src/app/services/sector-service/sector.service';
import { HttpErrorResponse } from '@angular/common/http';
import {FormControl, Validators, FormGroup, FormBuilder} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';



@Component({
  selector: 'app-add-sector',
  templateUrl: './add-sector.component.html',
  styleUrls: ['./add-sector.component.scss']
})
export class AddSectorComponent implements OnInit {

  hall: Hall;
  role = '';
  sectors = [new Sector(null, '', null, null, null, null)];
  sector = new Sector(null, '', null, null, null, null);
  disableSelect = new FormControl(false);
  addSector: FormGroup;
  sectorRows = new FormControl('', [Validators.required]);

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private hallService: HallService,
    private sectorService: SectorService,
    private toastr: ToastrService,
    private formBuilder: FormBuilder
  ) {
      this.addSector = this.formBuilder.group({
      name: ['', Validators.required],
      sectorRows: [Validators.required,  Validators.min(1)],
      sectorColumns: [Validators.required,  Validators.min(1)]      });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
        this.hallService.getHall(id).subscribe(
          (data: Hall) => {
            this.hall = data;
          }
          , (error: HttpErrorResponse) => {
          },
        );
    }
  }

  addOneMoreSector() {
    this.sectors.push(new Sector(null, '', 0, 0, null, null));
  }

  cancel() {
    this.router.navigate(['/hall/' + this.hall.id]);
  }

  add() {
      // tslint:disable-next-line: prefer-for-of
    for ( let i = 0; i < this.sectors.length; i++) {
      this.sector = this.sectors[i];
      this.sector.hallID = this.hall.id;
      this.sectorService.add(this.sector).subscribe(
      (data: any) => {
        this.sector = data;
        this.toastr.success('Successful add!');
        this.router.navigate(['/hall/' + this.hall.id]);
      }
      );
    }
  }
}
