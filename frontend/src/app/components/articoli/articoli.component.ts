import { Component, OnInit } from '@angular/core';
import { IArticleCard } from 'src/app/bricks/cards/article-card/article-card.component';

@Component({
  selector: 'app-articoli',
  templateUrl: './articoli.component.html',
  styleUrls: ['./articoli.component.css']
})
export class ArticoliComponent implements OnInit {

  articles: IArticleCard[] = []

  ngOnInit(): void {
    this.loadArticlesMock();
  }

  loadArticlesMock(){
    this.articles = [
      {
        urlRedirect: "https://valentinafilippini.it/legge-di-bilancio-2024/",
        title: "Legge di Bilancio 2024",
        description: "In data 30 dicembre 2023 è stata pubblicata La Legge di Bilancio 2024, Legge 213/2023. Molte sono le",
        publicationDate: new Date(),
        image: "assets/png/articles/Legge-di-Bilancio-2024_Cover-Sito-Linkedin-.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/naspi-nuova-assicurazione-sociale-per-limpiego/",
        title: "Naspi – Nuova assicurazione sociale per l’impiego",
        description: "Cos’è la NASPI La NASPI è la Nuova Assicurazione Sociale per l’Impiego. Consiste in una indennità mensile di",
        publicationDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-NASPI.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/permessi-allattamento-domande-frequenti/",
        title: "Permessi allattamento: domande frequenti",
        description: "Cosa sono? Chi può richiederli? Come si presenta la domanda? Fino a quando possono essere utilizzati? È prevista",
        publicationDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-5.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/divieto-licenziamento-dimissioni-naspi-papa/",
        title: "Divieto di licenziamento, dimissioni e NASPI per il papà",
        description: "Per il lavoratore padre che fruisce del congedo di paternità obbligatorio e/o del congedo di paternità alternativo, vige",
        publicationDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-licenziamento-e-dimissioni-papa-2023.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/esonero-contributivo-assunzione-donne-giovani-under-36/",
        title: "Esonero contributivo per assunzione di donne e giovani under 36",
        description: "La Legge di Bilancio 2023 ha previsto la proroga per l’anno 2023 dello sgravio contributivo per l’assunzione di",
        publicationDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-esonero-contributivo-assunzione-donne-e-giovani.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/assicurazione-casalinghe-2023/",
        title: "Assicurazione casalinghe 2023",
        description: "In data 31 gennaio 2023 scade il termine per il versamento del premio annuale di 24€ contro gli",
        publicationDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-casalinga-2023.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/divieto-licenziamento-dimissioni-naspi-papa/",
        title: "Divieto di licenziamento, dimissioni e NASPI per il papà",
        description: "Per il lavoratore padre che fruisce del congedo di paternità obbligatorio e/o del congedo di paternità alternativo, vige",
        publicationDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-licenziamento-e-dimissioni-papa-2023.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/esonero-contributivo-assunzione-donne-giovani-under-36/",
        title: "Esonero contributivo per assunzione di donne e giovani under 36",
        description: "La Legge di Bilancio 2023 ha previsto la proroga per l’anno 2023 dello sgravio contributivo per l’assunzione di",
        publicationDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-esonero-contributivo-assunzione-donne-e-giovani.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/assicurazione-casalinghe-2023/",
        title: "Assicurazione casalinghe 2023",
        description: "In data 31 gennaio 2023 scade il termine per il versamento del premio annuale di 24€ contro gli",
        publicationDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-casalinga-2023.png",
        readAllLabel: "Leggi Tutto »"
      }
    ]
  }

}
