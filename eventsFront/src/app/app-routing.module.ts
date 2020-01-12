import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';
import { ProfileComponent } from './core/profile/profile.component';
import { HomepageComponent } from './core/homepage/homepage.component';
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { AddEventComponent } from './core/add-event/add-event.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN|ROLE_REGULAR'}},
  { path: 'homepage', component: HomepageComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN|ROLE_REGULAR'}},
  { path: 'add-event', component: AddEventComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}}

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
