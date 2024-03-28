import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-privati',
  templateUrl: './privati.component.html',
  styleUrls: ['./privati.component.css']
})
export class PrivatiComponent implements OnInit {

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }

}
