import { Injectable, EventEmitter } from '@angular/core';
import { ErrorDialogData } from 'src/app/models/error-dialog-model/error-dialog-data';
import { ToastrService } from 'ngx-toastr';

@Injectable({
    providedIn: 'root',
})
export class ErrorDialogService {

  private static emitters: { [channel: string]: EventEmitter<any> } = {};
  static get(channel: string): EventEmitter<any> {
    if (!this.emitters[channel]) {
      this.emitters[channel] = new EventEmitter();
    }
    return this.emitters[channel];
  }


  // constructor() {}
  // // constructor(private toastr: ToastrService) { }

  // openDialog(errorInfo: ErrorDialogData): void {
  //   console.log('dosa');
  //   // this.toastr.error(errorInfo.message);
  // }
}
