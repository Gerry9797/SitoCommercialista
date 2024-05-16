import { Component, Input } from '@angular/core';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-inner-menu-area-riservata',
  templateUrl: './inner-menu-area-riservata.component.html',
  styleUrl: './inner-menu-area-riservata.component.css'
})
export class InnerMenuAreaRiservataComponent {

  @Input() activeItem : string = "bacheca"; 

  constructor(
    private utilityService: UtilityService
  ) {}

  logout(): void {
    this.utilityService.logout();
    // this.closeMobileMenu();
  }
  
}
