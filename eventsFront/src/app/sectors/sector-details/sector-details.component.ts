import { Component, OnInit } from '@angular/core';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { ActivatedRoute, Router } from '@angular/router';
import { SectorService } from 'src/app/services/sector-service/sector.service';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Hall } from 'src/app/models/hall-model/hall.model';

@Component({
  selector: 'app-sector-details',
  templateUrl: './sector-details.component.html',
  styleUrls: ['./sector-details.component.scss']
})
export class SectorDetailsComponent implements OnInit {

  sector: Sector;
  hall: number;
  role = '';
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sectorService: SectorService,
    private authService: AuthService
  ) { }

  updateHall() {
    this.router.navigate(['/updateSector/' + this.sector.id]);
  }

  cancel() {
    this.router.navigate(['/hall/' + this.hall ]);
  }

  ngOnInit() {
    this.role = this.authService.getUserRole();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.sectorService.getSector(id).subscribe(
        (data: Sector) => {
          this.sector = data;
          this.hall = data.hallID;
        }
        , (error: HttpErrorResponse) => {
        },
      );
    }
  }
}
