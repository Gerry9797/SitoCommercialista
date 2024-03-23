import { Component, OnInit } from '@angular/core';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-bacheca',
  templateUrl: './bacheca.component.html',
  styleUrls: ['./bacheca.component.css']
})
export class BachecaComponent implements OnInit {

    constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }

}
