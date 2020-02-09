import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';


import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { ActivatedRoute, Router } from '@angular/router';
import { MaterialModule } from 'src/app/material.module';
import { of, Observable } from 'rxjs';
import { Place } from 'src/app/models/place-model/place.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { AngularFireStorage } from 'angularfire2/storage';
import { PlaceDetailsComponent } from './place-details.component';
import { HallListItemComponent } from 'src/app/halls/hall-list-item/hall-list-item.component';
import { PlaceService } from 'src/app/services/place-service/place.service';

fdescribe('PlaceDetailsComponent', () => {
  let component: PlaceDetailsComponent;
  let fixture: ComponentFixture<PlaceDetailsComponent>;
  let placeService: any;
  let activatedRoute: any;
  let router: any;
  let authService: any;

  beforeEach(() => {
    const placeServiceMock = {
      getPlace: jasmine.createSpy('getPlace')
        .and.returnValue(of(new Place(1, 'Hala1', 'Sime Milosevica', 1, 1, [])))
    };

    const authServiceMock = {
      getUserRole: jasmine.createSpy('getUserRole')
        .and.returnValue(of('ROLE_ADMIN'))
    };

    const afStorageMock = {
    };

    const activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 };

    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [
        PlaceDetailsComponent,
        HallListItemComponent
      ],
      imports: [
        MaterialModule
      ],
      providers: [
        { provide: PlaceService, useValue: placeServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteStub },
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: authServiceMock },
      ]
    });
    fixture = TestBed.createComponent(PlaceDetailsComponent);
    component = fixture.componentInstance;
    placeService = TestBed.get(PlaceService);
    activatedRoute = TestBed.get(ActivatedRoute);
    authService = TestBed.get(AuthService);
    router = TestBed.get(Router);
  });
  it('should a fetch place', fakeAsync(() => {
    component.ngOnInit();
    expect(authService.getUserRole).toHaveBeenCalled();
    expect(placeService.getPlace).toHaveBeenCalledWith(1);
    tick();

    expect(component.place.id).toBe(1);
    expect(component.place.name).toBe('Hala1');
    expect(component.place.address).toBe('Sime Milosevica');
    expect(component.place.latitude).toBe(1);
    expect(component.place.longitude).toBe(1);


    fixture.detectChanges();
    tick();
    fixture.detectChanges();

  }));


  it('should navigate to the back page', () => {
    component.cancel();
    expect(router.navigate).toHaveBeenCalledWith(['/places']);
  });
});

