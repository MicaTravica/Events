import { ComponentFixture, async, TestBed } from '@angular/core/testing';
import { SectorDetailsComponent } from './sector-details.component';

describe('SectorDetailsComponent', () => {
    let component: SectorDetailsComponent;
    let fixture: ComponentFixture<SectorDetailsComponent>;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ SectorDetailsComponent ]
      })
      .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(SectorDetailsComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });
  });
