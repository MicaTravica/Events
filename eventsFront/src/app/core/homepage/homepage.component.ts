import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { Router, Event, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  admin;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { 
    this.router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.admin = this.authService.isAdmin();
      }
    });
  }

  ngOnInit() {
    this.admin = this.authService.isAdmin();
  }

}
