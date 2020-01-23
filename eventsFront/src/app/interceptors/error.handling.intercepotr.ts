
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

@Injectable()
export class ErrorHandlingInterceptor implements HttpInterceptor {

    constructor(private router: Router,
                public errorDialogService: ErrorDialogService) {}

    handleError(error: HttpErrorResponse) {
        let showDialog = true;
        let errorData: ErrorDialogData = null;
        if (error.error instanceof ErrorEvent) {
        // client-side error
            errorData = new ErrorDialogData('0', error.error.message);
        } else {
        // server-side error
            switch (error.status) {
                case 400:
                    // neki od nasih excepitona..
                    break;
                case 401:
                    this.router.navigate(['/login']);
                    showDialog = false;
                    break;
                case 403:     //forbidden
                    this.router.navigate(['/login']);
                    showDialog = false;
                    break;
                case 404:
                    this.router.navigate(['/notFoundPage']);
                    break;
            }
            errorData = new ErrorDialogData(error.status.toString(), error.message);
        }
        if (showDialog) {
            this.errorDialogService.openDialog(errorData);
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
