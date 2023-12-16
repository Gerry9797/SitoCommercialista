import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessButtonsComponent } from './business-buttons.component';

describe('BusinessButtonsComponent', () => {
  let component: BusinessButtonsComponent;
  let fixture: ComponentFixture<BusinessButtonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BusinessButtonsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BusinessButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
