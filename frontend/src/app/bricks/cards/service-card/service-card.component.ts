import { Component, Input } from '@angular/core';

export interface IServiceCard {
  redirectUrl: string,
  image: string;
  title: string,
  actionDescription: string
}

@Component({
  selector: 'app-service-card',
  templateUrl: './service-card.component.html',
  styleUrl: './service-card.component.css'
})
export class ServiceCardComponent {

  @Input() redirectUrl: string = "/privati";
  @Input() image: string = "assets/svg/consulenza_del_lavoro.svg";
  @Input() title: string = "CONSULENZA AI PRIVATI";
  @Input() actionDescription: string = "Scopri di più";
}
