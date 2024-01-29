import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntestazioneAreaRiservataComponent } from './intestazione-area-riservata.component';

describe('IntestazioneAreaRiservataComponent', () => {
  let component: IntestazioneAreaRiservataComponent;
  let fixture: ComponentFixture<IntestazioneAreaRiservataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [IntestazioneAreaRiservataComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(IntestazioneAreaRiservataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
