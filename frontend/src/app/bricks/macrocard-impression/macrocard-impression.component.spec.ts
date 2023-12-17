import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MacrocardImpressionComponent } from './macrocard-impression.component';

describe('MacrocardImpressionComponent', () => {
  let component: MacrocardImpressionComponent;
  let fixture: ComponentFixture<MacrocardImpressionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MacrocardImpressionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MacrocardImpressionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
