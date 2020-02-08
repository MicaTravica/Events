import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { EventDetailsComponent } from './event-details.component';
import { EventEntity } from 'src/app/models/event-model/event.model';
import { EventState } from 'src/app/models/event-model/event-state.enum';
import { EventType } from 'src/app/models/event-model/event-type.enum';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { EventService } from 'src/app/services/event-service/event.service';
import { MediaService } from 'src/app/services/media-service/media.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MaterialModule } from 'src/app/material.module';
import { AddressFormatPipe } from 'src/app/pipes/address-format.pipe';
import { DateFormatPipe } from 'src/app/pipes/date-format.pipe';
import { of, Observable } from 'rxjs';
import { Hall } from 'src/app/models/hall-model/hall.model';
import { Place } from 'src/app/models/place-model/place.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { AngularFireStorage } from 'angularfire2/storage';

fdescribe('EventDetailsComponent', () => {
  let component: EventDetailsComponent;
  let fixture: ComponentFixture<EventDetailsComponent>;
  let eventService: any;
  let mediaService: any;
  let activatedRoute: any;
  let router: any;
  let authService: any;
  let afStorage: any;

  beforeEach(() => {
    const eventServiceMock = {
      getEvent: jasmine.createSpy('getEvent')
        .and.returnValue(of(new EventEntity(1, 'Event', 'Some description', new Date(2021, 0O5, 0O5, 20, 0),
          new Date(2021, 0O5, 0O7, 23, 30), EventState.AVAILABLE, EventType.SPORT,
          [ new Hall(1, 'Hala', new Place(1, 'Arena', 'Nikole Tesle 10, Novi Sad', 10, 10), [])], [], [])))
    };

    const mediaServiceMock = {
      getMediaForEvent: jasmine.createSpy('getMediaForEvent')
        .and.returnValue(of(
          [
            { id: 1, path: 'https://opusteno.rs/slike/2013/10/kurs-crtanja-20063/kako-nacrtati-srce-4.jpg', eventId: 1 },
            { id: 2, path: 'https://opusteno.rs/slike/2013/08/kako-nacrtati-pile-crtanje-19625/kako-nacrtati-pile-42.jpg', eventId: 1 }
          ]
        ))
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
        EventDetailsComponent,
        AddressFormatPipe,
        DateFormatPipe
      ],
      imports: [
        MaterialModule
      ],
      providers: [
        { provide: EventService, useValue: eventServiceMock },
        { provide: MediaService, useValue: mediaServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteStub },
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: AngularFireStorage, useValue: afStorageMock }
      ]
    });
    fixture = TestBed.createComponent(EventDetailsComponent);
    component = fixture.componentInstance;
    eventService = TestBed.get(EventService);
    mediaService = TestBed.get(MediaService);
    activatedRoute = TestBed.get(ActivatedRoute);
    authService = TestBed.get(AuthService);
    afStorage = TestBed.get(AngularFireStorage);
    router = TestBed.get(Router);
  });

  it('should fetch event and his media list', fakeAsync(() => {
    component.ngOnInit();
    expect(authService.getUserRole).toHaveBeenCalled();
    expect(eventService.getEvent).toHaveBeenCalledWith(1);
    tick();

    expect(component.event.id).toBe(1);
    expect(component.event.name).toBe('Event');
    expect(component.event.description).toBe('Some description');
    expect(component.event.fromDate.getFullYear()).toBe(2021);
    expect(component.event.toDate.getFullYear()).toBe(2021);
    expect(component.event.eventState).toBe(EventState.AVAILABLE);
    expect(component.event.eventType).toBe(EventType.SPORT);
    expect(component.event.halls[0].place.name).toBe('Arena');

    fixture.detectChanges();
    tick();
    fixture.detectChanges();
    expect(mediaService.getMediaForEvent).toHaveBeenCalledWith(1);
    expect(component.event.mediaList.length).toBe(2);
  }));

  it('should navigate to the reservation page', () => {
    component.buyReserve();
    expect(router.navigate).toHaveBeenCalledWith(['/reservation/' + component.event.id]);
  });

  it('should inc active img', () => {
    const t = component.activeImg;
    component.next();
    expect(component.activeImg).toBe(t + 1);
  });

  it('should reduce active img', () => {
    const t = component.activeImg;
    component.previous();
    expect(component.activeImg).toBe(t - 1);
  });

});
