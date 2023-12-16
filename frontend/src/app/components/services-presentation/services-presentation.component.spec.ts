import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicesPresentationComponent } from './services-presentation.component';

describe('ServicesPresentationComponent', () => {
  let component: ServicesPresentationComponent;
  let fixture: ComponentFixture<ServicesPresentationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ServicesPresentationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ServicesPresentationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
