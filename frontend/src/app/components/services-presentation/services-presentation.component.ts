import { Component, OnInit } from '@angular/core';
import { IServiceCard } from 'src/app/bricks/cards/service-card/service-card.component';

@Component({
  selector: 'app-services-presentation',
  templateUrl: './services-presentation.component.html',
  styleUrl: './services-presentation.component.css'
})
export class ServicesPresentationComponent implements OnInit {

  serviceCards: IServiceCard[] = [];

  ngOnInit(): void {

    this.loadMockServiceCards();

  }


  loadMockServiceCards(){
    this.serviceCards = [
      {
        redirectUrl: '/privati',
        image: "assets/svg/consulenza_del_lavoro.svg",
        title: "CONSULENZA AI PRIVATI",
        actionDescription: "Scopri di più"
      },
      {
        redirectUrl: '/aziende',
        image: "assets/svg/collaboratori-domestici.svg",
        title: "GESTIONE COLLABORATORI DOMESTICI",
        actionDescription: "Scopri di più"
      },
      {
        redirectUrl: '/aziende',
        image: "assets/svg/elaborazione_paghe_e_contributi.svg",
        title: "ELABORAZIONE PAGHE E CONTRIBUTI",
        actionDescription: "Scopri di più"
      },
      {
        redirectUrl: '/aziende',
        image: "assets/svg/amministrazione_del_personale.svg",
        title: "AMMINISTRAZIONE DEL PERSONALE",
        actionDescription: "Scopri di più"
      }
    ]
  }
}
