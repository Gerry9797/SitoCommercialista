import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarAndTimeslotsComponent } from './calendar-and-timeslots.component';

describe('CalendarAndTimeslotsComponent', () => {
  let component: CalendarAndTimeslotsComponent;
  let fixture: ComponentFixture<CalendarAndTimeslotsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CalendarAndTimeslotsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalendarAndTimeslotsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
