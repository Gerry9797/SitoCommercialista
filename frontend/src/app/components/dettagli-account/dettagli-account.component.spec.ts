import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettagliAccountComponent } from './dettagli-account.component';

describe('DettagliAccountComponent', () => {
  let component: DettagliAccountComponent;
  let fixture: ComponentFixture<DettagliAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DettagliAccountComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DettagliAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
