import { AfterViewInit, Component, ElementRef, Input, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { PAESI_LIST } from 'src/app/enums/paesi.enum';


export interface AutoCompleteCompleteEvent {
  originalEvent: Event;
  query: string;
}

@Component({
  selector: 'app-select-autocomplete-field',
  templateUrl: './select-autocomplete-field.component.html',
  styleUrls: ['./select-autocomplete-field.component.css']
})
export class SelectAutocompleteFieldComponent implements OnInit {

  // @Input() optionsList = PAESI_LIST.map((elem) => elem.description);
  @Input() optionsList: any[] = PAESI_LIST;
  @Input() valueField: string = "description";
  @Input() descriptionField: string = "description";
  @Input() placeholder: string = "Seleziona un Paese/una regione..."

  selectedOption: any | undefined;

  filteredOptions: any[] = [];

  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;
  myControl = new FormControl('');


  filter(): void {
    const filterValue = this.input.nativeElement.value.toLowerCase();
    this.filteredOptions = this.optionsList.filter(o => o[this.descriptionField].toLowerCase().includes(filterValue));
  }

  constructor() { 
    this.filteredOptions = this.optionsList.slice();
  }

  ngOnInit(): void {
  }

}
