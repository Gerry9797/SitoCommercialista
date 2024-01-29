import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-inner-menu-area-riservata',
  templateUrl: './inner-menu-area-riservata.component.html',
  styleUrl: './inner-menu-area-riservata.component.css'
})
export class InnerMenuAreaRiservataComponent {

  @Input() activeItem : string = "bacheca"; 
  
}
