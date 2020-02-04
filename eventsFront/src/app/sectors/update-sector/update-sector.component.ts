import { Component, OnInit } from '@angular/core';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { ActivatedRoute, Router } from '@angular/router';
import { SectorService } from 'src/app/services/sector-service/sector.service';
import { HttpErrorResponse } from '@angular/common/http';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-sector',
  templateUrl: './update-sector.component.html',
  styleUrls: ['./update-sector.component.scss']
})
export class UpdateSectorComponent implements OnInit {
  sector: Sector;
  role = '';
  updateSector: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sectorService: SectorService,
    private toastr: ToastrService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.sectorService.getSector(id).subscribe(
        (data: Sector) => {
          this.sector = data;
          this.updateSector = this.formBuilder.group({
            name: [data.name, Validators.required],
            sectorRows: [Validators.required,  Validators.min(1)],
            sectorColumns: [Validators.required,  Validators.min(1)]});
        }
        , (error: HttpErrorResponse) => {
        },
      );
    }
  }

  update() {
    this.sectorService.update(this.sector).subscribe(
    (data: any) => {
      this.sector = data;
      this.toastr.success('Successful update!');
      this.router.navigate(['/sector/' + this.sector.id]);
    }
    );
  }

  cancel() {
    this.router.navigate(['/sector/' + this.sector.id]);
  }

}
