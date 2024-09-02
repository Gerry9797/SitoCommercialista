import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrenotaConsulenzaComponent } from './prenota-consulenza.component';

describe('PrenotaConsulenzaComponent', () => {
  let component: PrenotaConsulenzaComponent;
  let fixture: ComponentFixture<PrenotaConsulenzaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrenotaConsulenzaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PrenotaConsulenzaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
