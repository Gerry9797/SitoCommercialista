import { Component, OnInit } from '@angular/core';
import { NotificationMessage } from 'src/app/models/notification-message.model';

@Component({
  selector: 'app-indirizzi',
  templateUrl: './indirizzi.component.html',
  styleUrls: ['./indirizzi.component.css'],
})
export class IndirizziComponent implements OnInit {
  message: NotificationMessage | null = null;

  // message: NotificationMessage = {
  //   description: 'Indirizzo modificato correttamente.',
  //   status: 'message',
  // };

  address: any = true;

  constructor() {}

  ngOnInit(): void {}
}
