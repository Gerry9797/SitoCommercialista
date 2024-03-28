import { Component, Input } from '@angular/core';

export interface ICardWebinar {
  title: string;
  description: string;
  imgUrl: string;
  redirectUrl: string;
  actionLabel: string;
}

@Component({
  selector: 'app-webinar-card',
  templateUrl: './webinar-card.component.html',
  styleUrl: './webinar-card.component.css'
})
export class WebinarCardComponent {

  @Input() card: ICardWebinar = {
    title: "title",
    description: "description",
    imgUrl: "prova.svg",
    redirectUrl: "prova",
    actionLabel: "scopri di più"
  }

}
