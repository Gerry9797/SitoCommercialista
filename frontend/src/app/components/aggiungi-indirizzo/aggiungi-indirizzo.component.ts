import { Component, OnInit } from '@angular/core';
import { PAESI_LIST } from 'src/app/enums/paesi.enum';
import { PROVINCE_LIST } from 'src/app/enums/province.enum';
import { NotificationMessage } from 'src/app/models/notification-message.model';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-aggiungi-indirizzo',
  templateUrl: './aggiungi-indirizzo.component.html',
  styleUrls: ['./aggiungi-indirizzo.component.css']
})
export class AggiungiIndirizzoComponent implements OnInit {

  paesiList = PAESI_LIST;
  provinceList = PROVINCE_LIST;

  message: NotificationMessage | null = null;

  //   message: NotificationMessage = {
  //   description: "Indirizzo modificato correttamente.",
  //   status: "error"
  // }

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }

}
