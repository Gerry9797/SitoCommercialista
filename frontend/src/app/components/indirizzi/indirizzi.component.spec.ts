import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndirizziComponent } from './indirizzi.component';

describe('IndirizziComponent', () => {
  let component: IndirizziComponent;
  let fixture: ComponentFixture<IndirizziComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IndirizziComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndirizziComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
