import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebinarCardComponent } from './webinar-card.component';

describe('WebinarCardComponent', () => {
  let component: WebinarCardComponent;
  let fixture: ComponentFixture<WebinarCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WebinarCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WebinarCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
