import { Component, OnInit } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';

interface IFooterItem {
  title: string;
  redirectUrl: string;
}

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  recentNews: IFooterItem[] = [];
  serviziAziende: IFooterItem[] = [];
  serviziPrivati: IFooterItem[] = [];

  datiPersonali: any = SITE_CONFIG.datiPersonali;

  constructor() { }

  ngOnInit(): void {
    this.loadRecentNewsMock();
    this.loadServiziAziendeMock();
    this.loadServiziPrivatiMock();
  }

  loadRecentNewsMock(){
    this.recentNews = [
      {
        title: "Divieto di licenziamento, dimissioni e NASPI per il papà",
        redirectUrl: "https://valentinafilippini.it/divieto-licenziamento-dimissioni-naspi-papa/"
      },
      {
        title: "Esonero contributivo per assunzione di donne e giovani under 36",
        redirectUrl: "https://valentinafilippini.it/esonero-contributivo-assunzione-donne-giovani-under-36/"
      },
      {
        title: "Assicurazione casalinghe 2023",
        redirectUrl: "https://valentinafilippini.it/assicurazione-casalinghe-2023/"
      },
      {
        title: "Buoni benzina anno 2023",
        redirectUrl: "https://valentinafilippini.it/buoni-benzina-anno-2023/"
      },
      {
        title: "Busta paga più alta per il 2023",
        redirectUrl: "https://valentinafilippini.it/busta-paga-piu-alta-2023/"
      }
    ]
  }

  loadServiziAziendeMock(){
    this.serviziAziende = [
      {
        title: "Consulenza del lavoro e amministrazione del personale",
        redirectUrl: "aziende"
      },
      {
        title: "Elaborazione paghe e contributi",
        redirectUrl: "aziende"
      },
      {
        title: "Assunzione di un dipendente",
        redirectUrl: "aziende"
      }
    ]
  }

  loadServiziPrivatiMock(){
    this.serviziPrivati = [
      {
        title: "Consulenza Lavoratori",
        redirectUrl: "privati"
      },
      {
        title: "Consulenza Genitori",
        redirectUrl: "privati"
      },
      {
        title: "Dimissioni e periodo di preavviso (senza invio telematico)",
        redirectUrl: "privati"
      },
      {
        title: "Impariamo a leggere la tua busta paga",
        redirectUrl: "privati"
      },
      {
        title: "Assunzione collaboratore domestico",
        redirectUrl: "privati"
      }
    ]
  }

}
