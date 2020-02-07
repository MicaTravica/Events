import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';
import { ProfileComponent } from './core/profile/profile.component';
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { EventsListComponent } from './events/events-list/events-list.component';
import { AddEventComponent } from './events/add-event/add-event.component';
import { EventDetailsComponent } from './events/event-details/event-details.component';
import { ReservationComponent } from './tickets/reservation/reservation.component';
import { PaypalComponent } from './tickets/paypal/paypal.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ReservationListComponent } from './tickets/reservation-list/reservation-list.component';
import { TicketListComponent } from './tickets/ticket-list/ticket-list.component';
import { PlacesListComponent } from './places/places-list/places-list.component';
import { ReportsComponent } from './reports/reports.component';
import { PlaceDetailsComponent} from './places/place-details/place-details.component';
import { PlaceSearchComponent} from './places/place-search/place-search.component';
import { AddPlaceComponent } from './places/add-place/add-place.component';
import { AddHallComponent} from './halls/add-hall/add-hall.component';
import { UpdatePlaceComponent } from './places/update-place/update-place.component';
import { AddSectorComponent } from './sectors/add-sector/add-sector.component';
import { UpdateSectorComponent } from './sectors/update-sector/update-sector.component';
import { UpdateHallComponent} from './halls/update-hall/update-hall.component';
import { HallDetailsComponent } from './halls/hall-details/hall-details.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN|ROLE_REGULAR'}},
  { path: 'events', component: EventsListComponent },
  { path: 'places', component: PlacesListComponent },
  { path: 'add-place', component: AddPlaceComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'addHall/:id', component: AddHallComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'place', component: PlaceSearchComponent},
  { path: 'event/:id', component: EventDetailsComponent },
  { path: 'reports', component: ReportsComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'add-event', component: AddEventComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'reservation/:id' , component: ReservationComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_REGULAR'}},
  { path: 'reservations' , component: ReservationListComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_REGULAR'}},
  { path: 'tickets' , component: TicketListComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_REGULAR'}},
  { path: 'successPayPal' , component: PaypalComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_REGULAR'}},
  { path: 'failPayPal', component: PaypalComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_REGULAR'}},
  { path: 'reservation/:id' , component: ReservationComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_REGULAR'}},
  { path: 'place/:id' , component: PlaceDetailsComponent},
  { path: 'updatePlace/:id', component: UpdatePlaceComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'updateHall/:id', component: UpdateHallComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'addSector/:id', component: AddSectorComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'updateSector/:id', component: UpdateSectorComponent, canActivate: [RoleGuard], data: {expectedRoles: 'ROLE_ADMIN'}},
  { path: 'hall/:id', component: HallDetailsComponent},
  { path: '', redirectTo: 'events', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
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
