import { Component, OnInit } from '@angular/core';
import { ICardWebinar } from 'src/app/bricks/cards/webinar-card/webinar-card.component';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-webinar',
  templateUrl: './webinar.component.html',
  styleUrls: ['./webinar.component.css']
})
export class WebinarComponent implements OnInit {

  webinarCards: ICardWebinar[] = [];

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
    this.loadMockWebinarCards();
  }

  loadMockWebinarCards() {
    this.webinarCards = [
      {
        title: "Genitori lavoratori dipendenti - Diritti e tutele sul lavoro",
        description: "La formazione esclusiva per genitori e futuri genitori dipendenti che vogliono conoscere i propri diritti e tutele sul lavoro.",
        imgUrl: "assets/svg/wb1.jpg",
        redirectUrl: "https://valentinafilippini.it/webinar-per-genitori-lavoratori-dipendenti/",
        actionLabel: "Scopri di più"
      },
      {
        title: "Mamme con Partita IVA - Conciliare vita e lavoro",
        description: "Strumenti per mamme lavoratrici con partita iva che desiderano conciliare vita e lavoro nella libera professione.",
        imgUrl: "assets/svg/wb2.jpg",
        redirectUrl: "https://valentinafilippini.it/webinar/mamme-con-partita-iva-conciliare-vita-e-lavoro/",
        actionLabel: "Scopri di più"
      },
      {
        title: "Congedi Parentali - Dipendenti e Partite IVA",
        description: "L’incontro online che ti accompagnerà da subito nella comprensione dei congedi parentali, affinché tu possa usufruirne a tuo vantaggio!",
        imgUrl: "assets/svg/wb3.jpg",
        redirectUrl: "https://valentinafilippini.it/webinar/congedi-parentali-dipendenti-e-partite-iva/",
        actionLabel: "Scopri di più"
      },
    ]
  }

}
