import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsulenzaPrivatiComponent } from './consulenza-privati.component';

describe('ConsulenzaPrivatiComponent', () => {
  let component: ConsulenzaPrivatiComponent;
  let fixture: ComponentFixture<ConsulenzaPrivatiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsulenzaPrivatiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsulenzaPrivatiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
