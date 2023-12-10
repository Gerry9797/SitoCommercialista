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

  @Input() optionsList = PAESI_LIST.map((elem) => elem.description);

  selectedOption: any | undefined;

  filteredOptions: string[] = [];

  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;
  myControl = new FormControl('');


  filter(): void {
    const filterValue = this.input.nativeElement.value.toLowerCase();
    this.filteredOptions = this.optionsList.filter(o => o.toLowerCase().includes(filterValue));
  }

  constructor() { 
    this.filteredOptions = this.optionsList.slice();
  }

  ngOnInit(): void {
  }

  filterCountry(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < (this.optionsList as any[]).length; i++) {
        let currOption = (this.optionsList as any[])[i];
        if (currOption.description.toLowerCase().indexOf(query.toLowerCase()) == 0) {
            filtered.push(currOption);
        }
    }

    this.filteredOptions = filtered;
}

}
