import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiungiIndirizzoComponent } from './aggiungi-indirizzo.component';

describe('AggiungiIndirizzoComponent', () => {
  let component: AggiungiIndirizzoComponent;
  let fixture: ComponentFixture<AggiungiIndirizzoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AggiungiIndirizzoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AggiungiIndirizzoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
