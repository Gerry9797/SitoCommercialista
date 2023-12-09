import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingSpinnerClessidraComponent } from './loading-spinner-clessidra.component';

describe('LoadingSpinnerClessidraComponent', () => {
  let component: LoadingSpinnerClessidraComponent;
  let fixture: ComponentFixture<LoadingSpinnerClessidraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoadingSpinnerClessidraComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingSpinnerClessidraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
