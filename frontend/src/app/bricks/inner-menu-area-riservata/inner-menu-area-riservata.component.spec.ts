import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerMenuAreaRiservataComponent } from './inner-menu-area-riservata.component';

describe('InnerMenuAreaRiservataComponent', () => {
  let component: InnerMenuAreaRiservataComponent;
  let fixture: ComponentFixture<InnerMenuAreaRiservataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InnerMenuAreaRiservataComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InnerMenuAreaRiservataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
