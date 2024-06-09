import { Component, OnInit } from '@angular/core';
import { NotificationMessage } from 'src/app/models/notification-message.model';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-indirizzi',
  templateUrl: './indirizzi.component.html',
  styleUrls: ['./indirizzi.component.css'],
})
export class IndirizziComponent implements OnInit {

  address: any = false;

  constructor(
    private utilityService: UtilityService
  ) {}

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }
}
