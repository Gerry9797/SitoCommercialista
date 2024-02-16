import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainTitleDecorationComponent } from './main-title-decoration.component';

describe('MainTitleDecorationComponent', () => {
  let component: MainTitleDecorationComponent;
  let fixture: ComponentFixture<MainTitleDecorationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MainTitleDecorationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MainTitleDecorationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
