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
import { HomepageComponent } from './core/homepage/homepage.component'
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { AuthService } from './services/auth-service/auth.service';
import { EventService } from './services/event-service/event.service';
import { HallService } from './services/hall-service/hall.service';
import { PlaceService } from './services/place-service/place.service';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MenuComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    HomepageComponent,
    AddEventComponent
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
    EventService,
    AuthService,
    HallService,
    PlaceService,
    LoginGuard,
    RoleGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
