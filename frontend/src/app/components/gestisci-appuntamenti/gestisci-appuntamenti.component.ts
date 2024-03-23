import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-gestisci-appuntamenti',
  templateUrl: './gestisci-appuntamenti.component.html',
  styleUrls: ['./gestisci-appuntamenti.component.css']
})
export class GestisciAppuntamentiComponent implements OnInit {

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }

}
