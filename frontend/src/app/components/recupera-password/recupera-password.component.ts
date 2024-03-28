import { Component, OnInit } from '@angular/core';
import { NotificationMessage } from 'src/app/models/notification-message.model';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-recupera-password',
  templateUrl: './recupera-password.component.html',
  styleUrls: ['./recupera-password.component.css']
})
export class RecuperaPasswordComponent implements OnInit {

  message: NotificationMessage | null = null;

  // message: NotificationMessage = {
  //   description: "Nome utente o email non validi.",
  //   status: "error"
  // }

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
  }

}
