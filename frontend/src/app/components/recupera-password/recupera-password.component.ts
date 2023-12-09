import { Component, OnInit } from '@angular/core';
import { NotificationMessage } from 'src/app/models/notification-message.model';

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

  constructor() { }

  ngOnInit(): void {
  }

}
