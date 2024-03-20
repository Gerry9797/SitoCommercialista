import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainTitleCenterDecoratedComponent } from './main-title-center-decorated.component';

describe('MainTitleCenterDecoratedComponent', () => {
  let component: MainTitleCenterDecoratedComponent;
  let fixture: ComponentFixture<MainTitleCenterDecoratedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MainTitleCenterDecoratedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MainTitleCenterDecoratedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
