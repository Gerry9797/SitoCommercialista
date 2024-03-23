import { Component, OnInit } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-cookie-policy',
  templateUrl: './cookie-policy.component.html',
  styleUrls: ['./cookie-policy.component.css']
})
export class CookiePolicyComponent implements OnInit {

  sitoWebHttps: string = SITE_CONFIG.datiPersonali.contatti.sitoWebHttps;

  datiPersonali = SITE_CONFIG.datiPersonali;

  cookiePolicyInfo = SITE_CONFIG.cookiePolicy

  constructor(
    private utilityService: UtilityService
    ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }

}
