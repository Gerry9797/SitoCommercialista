import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrivatiComponent } from './privati.component';

describe('PrivatiComponent', () => {
  let component: PrivatiComponent;
  let fixture: ComponentFixture<PrivatiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrivatiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrivatiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
