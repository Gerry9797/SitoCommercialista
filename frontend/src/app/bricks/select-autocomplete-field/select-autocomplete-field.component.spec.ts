import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectAutocompleteFieldComponent } from './select-autocomplete-field.component';

describe('SelectAutocompleteFieldComponent', () => {
  let component: SelectAutocompleteFieldComponent;
  let fixture: ComponentFixture<SelectAutocompleteFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectAutocompleteFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectAutocompleteFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
