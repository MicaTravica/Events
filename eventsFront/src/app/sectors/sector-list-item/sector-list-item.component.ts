import { OnInit, Input, Component } from '@angular/core';
import { Sector } from 'src/app/models/sector-model/sector.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sector-list-item',
  templateUrl: './sector-list-item.component.html',
  styleUrls: ['./sector-list-item.component.scss']
})
export class SectorListItemComponent implements OnInit {

  @Input() sector: Sector;
  @Input() role: string;

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  updateHall() {
    this.router.navigate(['/updateSector/' + this.sector.id]);
  }
}
