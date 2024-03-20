import { Component, OnInit } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';

@Component({
  selector: 'app-cookie-policy',
  templateUrl: './cookie-policy.component.html',
  styleUrls: ['./cookie-policy.component.css']
})
export class CookiePolicyComponent implements OnInit {

  sitoWebHttps: string = SITE_CONFIG.datiPersonali.contatti.sitoWebHttps;

  datiPersonali = SITE_CONFIG.datiPersonali;

  constructor() { }

  ngOnInit(): void {
  }

}
