import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestisciAppuntamentiComponent } from './gestisci-appuntamenti.component';

describe('GestisciAppuntamentiComponent', () => {
  let component: GestisciAppuntamentiComponent;
  let fixture: ComponentFixture<GestisciAppuntamentiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestisciAppuntamentiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GestisciAppuntamentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
