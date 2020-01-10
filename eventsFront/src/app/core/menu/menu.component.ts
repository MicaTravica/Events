import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { NavigationEnd, Router, Event} from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  user: boolean;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { 
    this.router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.user = this.authService.isLoggedIn();
      }
    });
  }

  ngOnInit() {
  }

}
