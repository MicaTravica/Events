import { Component, OnInit } from '@angular/core';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { ActivatedRoute, Router } from '@angular/router';
import { SectorService } from 'src/app/services/sector-service/sector.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-sector',
  templateUrl: './update-sector.component.html',
  styleUrls: ['./update-sector.component.scss']
})
export class UpdateSectorComponent implements OnInit {

  sector: Sector;
  checked: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sectorService: SectorService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.sectorService.getSector(id).subscribe(
        (data: Sector) => {
          if (data.sectorColumns > 0) {
            this.checked = false;
            this.sector = data;
          } else {
            this.checked = true;
            this.sector = data;
            this.sector.sectorColumns = null;
            this.sector.sectorRows = null;
          }
        }
      );
    }
  }

  update() {
    this.sectorService.update(this.sector).subscribe(
      (data: Sector) => {
        this.toastr.success('Successful update!');
        this.router.navigate(['/hall/' + data.hallID]);
      });
  }

  parterre() {
    if (!this.checked) {
      this.sector.sectorRows = null;
      this.sector.sectorColumns = null;
    }
  }

  cancel() {
    this.router.navigate(['/hall/' + this.sector.hallID]);
  }

}
