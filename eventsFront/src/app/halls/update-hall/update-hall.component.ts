import { Component, OnInit } from '@angular/core';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { ActivatedRoute, Router } from '@angular/router';
import { HallService } from 'src/app/services/hall-service/hall.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-hall',
  templateUrl: './update-hall.component.html',
  styleUrls: ['./update-hall.component.scss']
})
export class UpdateHallComponent implements OnInit {

  hall: Hall;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private hallService: HallService,
    private toastr: ToastrService,
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.hallService.getHall(id).subscribe(
      (data: Hall) => {
        this.hall = data;
      });
    }
  }

  update() {
    this.hallService.update(this.hall).subscribe(
      (data: Hall) => {
        this.hall = data;
        this.toastr.success('Successful update!');
        this.router.navigate(['/hall/' + this.hall.id]);
      }
    );
  }

  cancel() {
    this.router.navigate(['/hall/' + this.hall.id]);
  }
}
