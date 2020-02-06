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
  sectors: Sector[];
  checked = [false];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hallService: HallService,
    private sectorService: SectorService,
    private toastr: ToastrService,
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
        this.hallService.getHall(id).subscribe(
          (data: Hall) => {
            this.hall = data;
            this.sectors = [new Sector(null, '', null, null, null, this.hall.id)];
          }
        );
    }
  }

  addOneMoreSector() {
    this.checked.push(false);
    this.sectors.push(new Sector(null, '', null, null, null, this.hall.id));
  }

  parterre(idx: number) {
    if (!this.checked[idx]) {
      this.sectors[idx].sectorRows = null;
      this.sectors[idx].sectorColumns = null;
    }
  }

  cancel() {
    this.router.navigate(['/hall/' + this.hall.id]);
  }

  add() {
      // tslint:disable-next-line: prefer-for-of
    for ( let i = 0; i < this.sectors.length; i++) {
      this.sectorService.add(this.sectors[i]).subscribe(
      (data: Sector) => {
        this.toastr.success('Successful add!');
        this.router.navigate(['/hall/' + data.hallID]);
      }
      );
    }
  }
}
