import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoticesBlockComponent } from './notices-block.component';

describe('NoticesBlockComponent', () => {
  let component: NoticesBlockComponent;
  let fixture: ComponentFixture<NoticesBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoticesBlockComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoticesBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
