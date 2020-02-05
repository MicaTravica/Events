import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSingleEventComponent } from './edit-single-event.component';

describe('EditSingleEventComponent', () => {
  let component: EditSingleEventComponent;
  let fixture: ComponentFixture<EditSingleEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditSingleEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSingleEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
