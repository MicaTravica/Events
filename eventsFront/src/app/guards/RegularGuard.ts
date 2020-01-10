import { CanActivate } from "@angular/router";
import { AuthService } from '../services/auth-service/auth.service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class RegularGuard implements CanActivate {

    constructor(
        private authService: AuthService
    ) {

    }

    canActivate(): boolean {
      return this.authService.isRegular();
    }
}