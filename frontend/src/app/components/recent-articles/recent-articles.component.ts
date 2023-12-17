import { Component, OnInit } from '@angular/core';
import { IArticleCard } from 'src/app/bricks/cards/article-card/article-card.component';

@Component({
  selector: 'app-recent-articles',
  templateUrl: './recent-articles.component.html',
  styleUrl: './recent-articles.component.css'
})
export class RecentArticlesComponent implements OnInit {

  articles: IArticleCard[] = []

  ngOnInit(): void {
    this.loadRecentArticlesMock();
  }

  loadRecentArticlesMock(){
    this.articles = [
      {
        urlRedirect: "https://valentinafilippini.it/divieto-licenziamento-dimissioni-naspi-papa/",
        title: "Divieto di licenziamento, dimissioni e NASPI per il papà",
        description: "Per il lavoratore padre che fruisce del congedo di paternità obbligatorio e/o del congedo di paternità alternativo, vige",
        publicationhDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-licenziamento-e-dimissioni-papa-2023.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/esonero-contributivo-assunzione-donne-giovani-under-36/",
        title: "Esonero contributivo per assunzione di donne e giovani under 36",
        description: "La Legge di Bilancio 2023 ha previsto la proroga per l’anno 2023 dello sgravio contributivo per l’assunzione di",
        publicationhDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-esonero-contributivo-assunzione-donne-e-giovani.png",
        readAllLabel: "Leggi Tutto »"
      },
      {
        urlRedirect: "https://valentinafilippini.it/assicurazione-casalinghe-2023/",
        title: "Assicurazione casalinghe 2023",
        description: "In data 31 gennaio 2023 scade il termine per il versamento del premio annuale di 24€ contro gli",
        publicationhDate: new Date(),
        image: "assets/png/articles/Cover-Sito-Linkedin-casalinga-2023.png",
        readAllLabel: "Leggi Tutto »"
      }
    ]
  }

}
