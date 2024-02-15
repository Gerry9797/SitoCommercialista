import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainTitleCenterComponent } from './main-title-center.component';

describe('MainTitleCenterComponent', () => {
  let component: MainTitleCenterComponent;
  let fixture: ComponentFixture<MainTitleCenterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MainTitleCenterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MainTitleCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
