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

import { UserService } from './services/user-service/user.service';
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { AuthService } from './services/auth-service/auth.service';
import { TicketService } from './services/ticket-service/ticket.service';
import { EventsListComponent } from './events/events-list/events-list.component';
import { EventDetailsComponent } from './events/event-details/event-details.component';
import { EventListItemComponent } from './events/event-list-item/event-list-item.component';
import { EventService } from './services/event-service/event.service';


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
    EventListItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule
  ],
  providers: [
    UserService,
    AuthService,
    TicketService,
    EventService,
    LoginGuard,
    RoleGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
