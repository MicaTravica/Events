import { UpdateHallComponent } from './update-hall.component';
import { TestBed, async, ComponentFixture } from '@angular/core/testing';

describe('UpdateHallComponent', () => {
    let component: UpdateHallComponent;
    let fixture: ComponentFixture<UpdateHallComponent>;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ UpdateHallComponent ]
      })
      .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UpdateHallComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });
  });
