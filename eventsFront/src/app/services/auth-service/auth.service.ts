import { Injectable } from '@angular/core';
import { ROLE_ADMIN, ROLE_REGULAR } from './constants';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor() { }

  isLoggedIn() {
    return localStorage.getItem('token') !== null;
  };

  isAdmin() {
    return JSON.parse(localStorage.getItem('user')).userRole === ROLE_ADMIN;
  }

  isRegular() {
    return JSON.parse(localStorage.getItem('user')).userRole === ROLE_REGULAR;
  }
}
