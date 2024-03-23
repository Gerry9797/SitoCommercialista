import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {


  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }



}
