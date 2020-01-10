import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { Router, Event, NavigationStart, NavigationEnd, NavigationError } from '@angular/router';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  user: boolean;
  guest: boolean;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.user = this.authService.isLoggedIn();
        this.guest = !this.user;
      }
    });
  }

  ngOnInit() {
    this.user = this.authService.isLoggedIn();
    this.guest = !this.user;
  }

  logout() {
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

}
