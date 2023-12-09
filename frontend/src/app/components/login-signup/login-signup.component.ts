import { Component, OnInit } from '@angular/core';
import { NotificationMessage } from 'src/app/models/notification-message.model';

@Component({
  selector: 'app-login-signup',
  templateUrl: './login-signup.component.html',
  styleUrls: ['./login-signup.component.css']
})
export class LoginSignupComponent implements OnInit {

  message: NotificationMessage | null = null;

  // message: NotificationMessage = {
  //   description: "Indirizzo email sconosciuto. Ricontrolla o prova ad usare il tuo nome utente.",
  //   status: "error"
  // }

  constructor() { }

  ngOnInit(): void {
  }

}
