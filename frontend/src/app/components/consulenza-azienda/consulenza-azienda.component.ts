import { Component, OnInit } from '@angular/core';
import { ConsulenzaCardModel } from 'src/app/models/consulenza-card.model';

@Component({
  selector: 'app-consulenza-azienda',
  templateUrl: './consulenza-azienda.component.html',
  styleUrl: './consulenza-azienda.component.css'
})
export class ConsulenzaAziendaComponent implements OnInit{

  consulenzaAziendaCards: ConsulenzaCardModel[] = [];
  
  ngOnInit(): void {
    this.loadMockCardsConsulenzaAzienda();
  }

  loadMockCardsConsulenzaAzienda(){
    this.consulenzaAziendaCards = [
      {
        title: "Vuoi assumere un dipendente?",
        description: "Sei un datore di lavoro e hai la necessità di assumenre personale dipendente che ti aiuti nella gestione della tua attività? Contattami per una consulenza iniziale e vediamo qual è la miglior soluzione per la tua realtà. Se deciderai di diventare cliente del mio studio ti verrà fornito un preventivo personalizzato per la gestione dell'amministrazione del personale e consulenza del lavoro.",
        imgUrl: "assets/svg/consulenza_aziendale_1.svg",
        price: "130,00 €",
        time: "55 min",
        redirectUrl: "/aziende/consulenza/assunzione-di-un-dipendente/"
      },
      {
        title: "Consulenza Azienda",
        description: "Sei una azienda e hai necessità di una consulenza online? Fissiamo questo primo incontro online dove potremmo affrontare una prima consulenza online personalizzata.",
        imgUrl: "assets/svg/consulenza_aziendale_1.svg",
        price: "130,00 €",
        time: "55 min",
        redirectUrl: "/aziende/consulenza/assunzione-di-un-dipendente/"
      }
    ]
  }


}
