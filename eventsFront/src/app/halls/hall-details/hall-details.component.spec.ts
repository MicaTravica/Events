import { HallDetailsComponent } from './hall-details.component';
import { ComponentFixture, async, TestBed } from '@angular/core/testing';

describe('HallDetailsComponent', () => {
    let component: HallDetailsComponent;
    let fixture: ComponentFixture<HallDetailsComponent>;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ HallDetailsComponent ]
      })
      .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(HallDetailsComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });
  });
