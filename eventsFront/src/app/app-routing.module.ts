import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';
import { ProfileComponent } from './core/profile/profile.component';
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { EventsListComponent } from './events/events-list/events-list.component';
import { AddEventComponent } from './core/add-event/add-event.component';
import { EventDetailsComponent } from './events/event-details/event-details.component';
import { ReservationComponent } from './tickets/reservation/reservation.component';
import { PaypalComponent } from './tickets/paypal/paypal.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN|ROLE_REGULAR'}},
  { path: 'events', component: EventsListComponent },
  { path: 'event/:id', component: EventDetailsComponent },
  { path: 'add-event', component: AddEventComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'reservation/:id' , component: ReservationComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_REGULAR'}},
  { path: 'successPayPal' , component: PaypalComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_REGULAR'}},
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
