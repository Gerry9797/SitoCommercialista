import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {

  revisoreLegaleFlag: boolean = SITE_CONFIG.revisoreLegale
  siteConfig = SITE_CONFIG

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }



}
