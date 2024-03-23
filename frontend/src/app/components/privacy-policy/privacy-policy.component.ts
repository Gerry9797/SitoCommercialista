import { Component, OnInit } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-privacy-policy',
  templateUrl: './privacy-policy.component.html',
  styleUrls: ['./privacy-policy.component.css']
})
export class PrivacyPolicyComponent implements OnInit {

  datiPersonali = SITE_CONFIG.datiPersonali;

  privacyPolicyInfo = SITE_CONFIG.privacyPolicy

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }

}
