import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileComponent } from './profile.component';
import { FormBuilder, FormsModule, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UserService } from 'src/app/services/user-service/user.service';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { ToastrService, ToastrModule } from 'ngx-toastr';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/models/user-model/user.model';
import { of } from 'rxjs/internal/observable/of';
import { MaterialModule } from 'src/app/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';

fdescribe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;

  let router: Router;
  let userService: any;
  let authService: any;
  let toastr: ToastrService;
  let httpClient: any;

  beforeEach(async(() => {
    const spyAuthService = jasmine.createSpyObj('AuthService', ['getToken']);
    const spyRouter = jasmine.createSpyObj('Router', ['navigateByUrl']);
    const httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'put']);
    const spyUserService = new UserService(httpClientSpy, spyAuthService);
    TestBed.configureTestingModule({
      declarations: [ ProfileComponent ],
      providers : [
        ToastrService,
        FormBuilder,
        { provide: UserService, useValue: spyUserService},
        { provide: AuthService, useValue: spyAuthService},
        { provide: Router, useValue: spyRouter},
        { provide: HttpClient, useValue: httpClientSpy}
      ],
      imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ToastrModule.forRoot(),
        RouterModule,
        BrowserAnimationsModule
      ]
    });
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    authService = TestBed.get(AuthService);
    toastr = TestBed.get(ToastrService);
    router = TestBed.get(Router);
    httpClient = TestBed.get(HttpClient);
  }));

  it('should populate form data with onInit', () => {

    const tokenVal = 'token_sam_ali_najlepsi';
    const myData: User = new User(1, 'Dusan', 'Bucan', 'dusanbzr@gmail.com', '123-12', 'DusanB');

    httpClient.get.and.returnValue(of(myData));
    authService.getToken.and.returnValue(tokenVal);

    // ovo bi trebalo da pozove metodu iz userService-a me kojem sam prosledio 2
    // spy objekta i cije metode su iznad defnisane kako treba da se ponasaju...
    component.ngOnInit();

    // proveri koliko puta je poslan get zahtev i koliko puta je trazio token iz authService-a
    expect(httpClient.get.calls.count()).toBe(1, 'httpClient method was called once');
    expect(authService.getToken.calls.count()).toBe(1, 'getToken method was called once');

    // proveri da li mokovane metode dobro rade---> da li si ih dobro mokovao
    expect(authService.getToken.calls.mostRecent().returnValue).toBe(tokenVal);

    // da li su polja forme popunjena validno..
    expect(component.getUserId()).toBe(myData.id);
    expect(component.name.value).toBe(myData.name);
    expect(component.surname.value).toBe(myData.surname);
    expect(component.email.value).toBe(myData.email);
    expect(component.phone.value).toBe(myData.phone);
    expect(component.username.value).toBe(myData.username);
  });

  it('should return updated user token from backEnd', () => {

    const tokenVal = 'token_sam_ali_najlepsi';
    const myData: User = new User(1, 'Dusan', 'Bucan', 'dusanbzr@gmail.com', '123-12', 'DusanB');
    const myNewData: User = new User(1, 'Dusann', 'Bucann', 'ddusanbzr@gmail.com', '123-123', 'DusanBB');

    httpClient.get.and.returnValue(of(myData));
    httpClient.put.and.returnValue(of(tokenVal));
    authService.getToken.and.returnValue(tokenVal);

    // ovo bi trebalo da pozove metodu iz userService-a me kojem sam prosledio 2
    // spy objekta i cije metode su iznad defnisane kako treba da se ponasaju...
    component.ngOnInit();

    // izemenjene vrednosti kao da je korisnik unosio
    component.name.setValue(myNewData.name);
    component.surname.setValue(myNewData.surname);
    component.email.setValue(myNewData.email);
    component.phone.setValue(myNewData.phone);
    component.username.setValue(myNewData.username);

    component.onSubmit();
    expect(localStorage.getItem('token')).toBe(tokenVal);
  });

  it('should navigate to changePassword component', () => {

    const tokenVal = 'token_sam_ali_najlepsi';
    const myData: User = new User(1, 'Dusan', 'Bucan', 'dusanbzr@gmail.com', '123-12', 'DusanB');
    const myNewData: User = new User(1, 'Dusann', 'Bucann', 'ddusanbzr@gmail.com', '123-123', 'DusanBB');

    httpClient.get.and.returnValue(of(myData));
    httpClient.put.and.returnValue(of(myNewData));
    authService.getToken.and.returnValue(tokenVal);

    component.ngOnInit();
    component.changePassword();
    expect(router.navigateByUrl).toHaveBeenCalledTimes(1);
  });



});
