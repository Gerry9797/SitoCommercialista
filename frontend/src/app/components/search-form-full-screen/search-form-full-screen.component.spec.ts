import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchFormFullScreenComponent } from './search-form-full-screen.component';

describe('SearchFormFullScreenComponent', () => {
  let component: SearchFormFullScreenComponent;
  let fixture: ComponentFixture<SearchFormFullScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchFormFullScreenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SearchFormFullScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
