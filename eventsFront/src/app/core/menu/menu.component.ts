import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { NavigationEnd, Router, Event} from '@angular/router';
import { ErrorDialogService } from 'src/app/services/error-dialog-service/error-dialog.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit, AfterViewInit {

  role: string;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.role = this.authService.getUserRole();
      }
    });
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    ErrorDialogService.get('1').subscribe(
      value => {
        this.toastr.success(value);
      }
    );

    ErrorDialogService.get('2').subscribe(
      value => {
        this.router.navigate([value]);
      }
    );
  }

  showEvents() {
    this.router.navigate(['/events']);
  }

  showPlaces() {
    this.router.navigate(['/places']);
  }

  showReports() {
    this.router.navigate(['/reports']);
  }

  showReservations() {
    this.router.navigate(['/reservations']);
  }

  showTickets() {
    this.router.navigate(['/tickets']);
  }
}
