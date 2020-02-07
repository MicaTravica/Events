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
  let userService: jasmine.SpyObj<UserService>;
  let authService: jasmine.SpyObj<AuthService>;
  let toastr: ToastrService;
  let httpClient: jasmine.SpyObj<HttpClient>;

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
        { provide: UserService, value: spyUserService},
        { provide: AuthService, value: spyAuthService},
        { provide: Router, value: spyRouter},
        { provide: HttpClient, value: httpClientSpy}
      ],
      imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ToastrModule.forRoot(),
        RouterModule
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    // component = TestBed.get(ProfileComponent);
    userService = TestBed.get(UserService);
    authService = TestBed.get(AuthService);
    toastr = TestBed.get(ToastrService);
    router = TestBed.get(Router);
    httpClient = TestBed.get(HttpClient);
  }));

  it('should populate form data with onInit', () => {

    const tokenVal = 'token_sam_ali_najlepsi';
    const myData: User = new User(1, 'Dusan', 'Bucan', 'dusanbzr@gmail.com', '123-12', 'DusanB');
    console.log(myData);

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
    // expect(httpClient.get.calls.mostRecent().returnValue).toBe(of(myData));

    // da li su polja forme popunjena validno..
    expect(component.getUserId()).toBe(myData.id);
    expect(component.name.value).toBe(myData.name);
    expect(component.surname.value).toBe(myData.surname);
    expect(component.email.value).toBe(myData.email);
    expect(component.phone.value).toBe(myData.phone);
    expect(component.username.value).toBe(myData.username);

  });


});
