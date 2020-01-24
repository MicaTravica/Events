import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ErrorDialogService } from './services/error-dialog-service/error-dialog.service';
import { EventChannels } from './models/event-channels/eventChannels';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {
  title = 'eventsFront';

  constructor(
      private router: Router,
      private toastr: ToastrService
  ) {}

  ngAfterViewInit(): void {
    ErrorDialogService.get(EventChannels.ERROR_MESSAGE).subscribe(
      (value: string) => {
        this.toastr.error(value);
      }
    );

    ErrorDialogService.get(EventChannels.REDIRECT_EVENT).subscribe(
      (value: string) => {
        this.router.navigate([value]);
      }
    );
  }
}
