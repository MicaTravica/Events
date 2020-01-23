
import { Injectable} from '@angular/core';
import {
    HttpInterceptor,
    HttpRequest,
    HttpResponse,
    HttpHandler,
    HttpEvent,
    HttpErrorResponse
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ErrorDialogService } from '../services/error-dialog-service/error-dialog.service';
import { ErrorDialogData } from '../models/error-dialog-model/error-dialog-data';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class ErrorHandlingInterceptor implements HttpInterceptor {

    constructor(private router: Router,
                private toastr: ToastrService) {}

    handleError(error: HttpErrorResponse) {
        let errorData: ErrorDialogData = null;
        if (error.error instanceof ErrorEvent) {
        // client-side error
            errorData = new ErrorDialogData('0', error.error.message);
        } else {
        // server-side error
            switch (error.status) {
                case 400:
                    // neki od nasih excepitona..
                    // console.log(error.error);

                    ErrorDialogService.get('1').emit(error.error);
                    break;
                case 401:
                    ErrorDialogService.get('2').emit('/login');
                    break;
                case 403:     // forbidden
                    ErrorDialogService.get('2').emit('/login');
                    break;
                case 404:
                    ErrorDialogService.get('2').emit(error.error);
                    break;
                default:
                    break;
            }
            errorData = new ErrorDialogData(error.status.toString(), error.message);
        }
        return throwError(errorData);
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request)
        .pipe(
            catchError(this.handleError)
        );
    }

}
