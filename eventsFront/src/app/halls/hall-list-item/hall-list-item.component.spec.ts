import { HallListItemComponent } from './hall-list-item.component';
import { TestBed, async, ComponentFixture } from '@angular/core/testing';

describe('HallListItemComponent', () => {
    let component: HallListItemComponent;
    let fixture: ComponentFixture<HallListItemComponent>;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ HallListItemComponent ]
      })
      .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(HallListItemComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });
  });
