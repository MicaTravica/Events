import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';
import { ProfileComponent } from './core/profile/profile.component';
import { HomepageComponent } from './core/homepage/homepage.component';

import { GuestGuard } from './guards/GuestGuard'
import { UserGuard } from './guards/UserGuard'
import { AdminGuard } from './guards/AdminGuard'
import { RegularGuard } from './guards/RegularGuard'

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [GuestGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [GuestGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [UserGuard] },
  { path: 'homepage', component: HomepageComponent, canActivate: [UserGuard] },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AppRoutingModule { }
