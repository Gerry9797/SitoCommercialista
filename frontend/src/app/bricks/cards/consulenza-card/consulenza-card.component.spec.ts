import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsulenzaCardComponent } from './consulenza-card.component';

describe('ConsulenzaCardComponent', () => {
  let component: ConsulenzaCardComponent;
  let fixture: ComponentFixture<ConsulenzaCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsulenzaCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsulenzaCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
