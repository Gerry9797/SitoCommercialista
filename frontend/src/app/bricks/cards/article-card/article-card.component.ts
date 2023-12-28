import { Component, Input, OnInit } from '@angular/core';

export interface IArticleCard {
  urlRedirect: string,
  image: string,
  title: string,
  description: string,
  publicationDate: Date,
  readAllLabel: string
}

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrl: './article-card.component.css'
})
export class ArticleCardComponent implements OnInit {

  @Input() article!: IArticleCard

  ngOnInit(): void {
  }

}
