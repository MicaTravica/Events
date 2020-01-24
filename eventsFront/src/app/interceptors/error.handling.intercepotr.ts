
import { Injectable} from '@angular/core';
import {
    HttpInterceptor,
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpErrorResponse
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ErrorDialogService } from '../services/error-dialog-service/error-dialog.service';
import { ErrorDialogData } from '../models/error-dialog-model/error-dialog-data';
import { EventChannels } from '../models/event-channels/eventChannels';


@Injectable()
export class ErrorHandlingInterceptor implements HttpInterceptor {

    constructor() {}

    handleError(error: HttpErrorResponse) {
        let errorData: ErrorDialogData = null;
        if (error.error instanceof ErrorEvent) {
            errorData = new ErrorDialogData('0', error.error.message);
        } else {
        // server-side error
            switch (error.status) {
                case 400:
                    ErrorDialogService.get(EventChannels.ERROR_MESSAGE).emit(error.error);
                    break;
                case 401:
                    ErrorDialogService.get(EventChannels.REDIRECT_EVENT).emit('/login');
                    break;
                case 403:
                    ErrorDialogService.get(EventChannels.REDIRECT_EVENT).emit('/login');
                    break;
                case 404:
                    ErrorDialogService.get(EventChannels.REDIRECT_EVENT).emit(error.error);
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
