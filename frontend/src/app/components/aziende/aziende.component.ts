import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-aziende',
  templateUrl: './aziende.component.html',
  styleUrls: ['./aziende.component.css']
})
export class AziendeComponent implements OnInit {

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }

}
