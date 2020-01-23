import { Injectable } from '@angular/core';
import { ErrorDialogData } from 'src/app/models/error-dialog-model/error-dialog-data';
import { ToastrService } from 'ngx-toastr';

@Injectable({
    providedIn: 'root',
})
export class ErrorDialogService {

  constructor() {}
  // constructor(private toastr: ToastrService) { }

  openDialog(errorInfo: ErrorDialogData): void {
    console.log('dosa');
    // this.toastr.error(errorInfo.message);
  }
}
