import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

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
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { ReportsComponent } from './reports/reports.component';
import { ChartsModule } from 'ng2-charts';
import { ReportService } from './services/report-service/report.service';
import { ErrorHandlingInterceptor } from './interceptors/error.handling.intercepotr';
import { ErrorDialogService } from './services/error-dialog-service/error-dialog.service';
import { Router } from '@angular/router';
import { AngularFireModule } from 'angularfire2'
import { AngularFireStorageModule } from 'angularfire2/storage'
import { PlaceListItemComponent} from './places/place-list-item/place-list-item.component';
import { PlaceDetailsComponent} from './places/place-details/place-details.component';
import { PlaceSearchComponent} from './places/place-search/place-search.component';
import { AddPlaceComponent } from './places/add-place/add-place.component';
import { AddHallComponent} from './halls/add-hall/add-hall.component';
import { UpdatePlaceComponent} from './places/update-place/update-place.component';
import { UpdateHallComponent} from './halls/update-hall/update-hall.component';
import { AddSectorComponent } from './sectors/add-sector/add-sector.component';
import { UpdateSectorComponent} from './sectors/update-sector/update-sector.component';
import { HallDetailsComponent} from './halls/hall-details/hall-details.component';
import { HallListItemComponent} from './halls/hall-list-item/hall-list-item.component';
import { SectorListItemComponent} from './sectors/sector-list-item/sector-list-item.component';
import { SectorDetailsComponent} from './sectors/sector-details/sector-details.component';

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
    ReportsComponent,
    PlaceDetailsComponent,
    PlaceListItemComponent,
    PlaceSearchComponent,
    AddPlaceComponent,
    AddHallComponent,
    UpdatePlaceComponent,
    UpdateHallComponent,
    AddSectorComponent,
    UpdateSectorComponent,
    HallDetailsComponent,
    HallListItemComponent,
    SectorListItemComponent,
    SectorDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    ChartsModule,
    AngularFireModule.initializeApp({
      apiKey: "AIzaSyDvlKsDLvsnyKJpHoBBEpqSA7aQbVqm7r4",
      authDomain: "events-c8789.firebaseapp.com",
      storageBucket: "events-c8789.appspot.com",
      projectId: "events-c8789",
    }),
    AngularFireStorageModule
  ],
  providers: [
    ToastrService,
    UserService,
    EventService,
    AuthService,
    TicketService,
    EventService,
    HallService,
    PlaceService,
    LoginGuard,
    RoleGuard,
    ReportService,
    ErrorDialogService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlingInterceptor,
      multi: true,
      deps: [ErrorDialogService, Router]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
