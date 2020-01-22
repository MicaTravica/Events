import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';

import { HeaderComponent } from './core/header/header.component';
import { MenuComponent } from './core/menu/menu.component';
import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';
import { ProfileComponent } from './core/profile/profile.component';
import { AddEventComponent } from './core/add-event/add-event.component';

import { UserService } from './services/user-service/user.service';
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { AuthService } from './services/auth-service/auth.service';
import { TicketService } from './services/ticket-service/ticket.service';
import { EventsListComponent } from './events/events-list/events-list.component';
import { EventDetailsComponent } from './events/event-details/event-details.component';
import { EventListItemComponent } from './events/event-list-item/event-list-item.component';
import { EventService } from './services/event-service/event.service';
import { HallService } from './services/hall-service/hall.service';
import { PlaceService } from './services/place-service/place.service';
import { DateFormatPipe } from './pipes/date-format.pipe';
import { EventSearchComponent } from './events/event-search/event-search.component';
import { PaginationComponent } from './pagination/pagination.component';
import { AddressFormatPipe } from './pipes/address-format.pipe';
import { ReservationComponent } from './tickets/reservation/reservation.component';
import { PaypalComponent } from './tickets/paypal/paypal.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ReservationListComponent } from './tickets/reservation-list/reservation-list.component';
import { TicketListComponent } from './tickets/ticket-list/ticket-list.component';
import { PlacesListComponent } from './places/places-list/places-list.component';
import { ToastrModule } from 'ngx-toastr';
import { ReportsComponent } from './reports/reports.component';
import { ChartsModule } from 'ng2-charts';
import { ReportService } from './services/report-service/report.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MenuComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    EventsListComponent,
    EventDetailsComponent,
    EventListItemComponent,
    AddEventComponent,
    DateFormatPipe,
    EventSearchComponent,
    PaginationComponent,
    ReservationComponent,
    PaypalComponent,
    AddressFormatPipe,
    PageNotFoundComponent,
    ReservationListComponent,
    TicketListComponent,
    PlacesListComponent,
    ReportsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    ChartsModule
  ],
  providers: [
    UserService,
    EventService,
    AuthService,
    TicketService,
    EventService,
    HallService,
    PlaceService,
    LoginGuard,
    RoleGuard,
    ReportService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
