import { OnInit, Input, Component } from '@angular/core';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hall-list-item',
  templateUrl: './hall-list-item.component.html',
  styleUrls: ['./hall-list-item.component.scss']
})
export class HallListItemComponent implements OnInit {

  @Input() hall: Hall;

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  showDetails() {
    this.router.navigate(['/hall/' + this.hall.id]);
  }
}
