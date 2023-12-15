import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsulenzaAziendaComponent } from './consulenza-azienda.component';

describe('ConsulenzaAziendaComponent', () => {
  let component: ConsulenzaAziendaComponent;
  let fixture: ComponentFixture<ConsulenzaAziendaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsulenzaAziendaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsulenzaAziendaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
