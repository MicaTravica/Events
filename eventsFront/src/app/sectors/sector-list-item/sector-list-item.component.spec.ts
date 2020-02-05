import { SectorListItemComponent } from './sector-list-item.component';
import { ComponentFixture, async, TestBed } from '@angular/core/testing';

describe('SectorListItemComponent', () => {
    let component: SectorListItemComponent;
    let fixture: ComponentFixture<SectorListItemComponent>;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ SectorListItemComponent ]
      })
      .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(SectorListItemComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });
  });
