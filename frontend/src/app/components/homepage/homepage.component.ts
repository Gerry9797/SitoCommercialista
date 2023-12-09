import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  @ViewChild('citazioneContainer', { static: false }) citazioneContainer!: ElementRef;
  @ViewChild('iconCitazioneContainer', { static: false }) iconCitazioneContainer!: ElementRef;
  @ViewChild('citazioneDescriptionContainer', { static: false }) citazioneDescriptionContainer!: ElementRef;
  @ViewChild('lastArticlesSectionParent', { static: false }) lastArticlesSectionParent!: ElementRef;
  @ViewChild('lastArticlesSection', { static: false }) lastArticlesSection!: ElementRef;

  constructor() { }

  ngOnInit(): void {
    this.handleAnimations();
  }

  handleAnimations(){
    this.mostraCitazioneInCard();
    this.mostraIconCitazioneInCard();
    this.mostraCitazioneDescriptionInCard();
    this.mostraLastArticlesSectionParent();
    this.mostraLastArticlesSection();
  }

  mostraCitazioneInCard(){
    setTimeout(() => {
      if (this.citazioneContainer && this.citazioneContainer.nativeElement.classList.contains('elementor-invisible')) {
        this.citazioneContainer.nativeElement.classList.remove('elementor-invisible');
        this.citazioneContainer.nativeElement.classList.add('animated');
        this.citazioneContainer.nativeElement.classList.add('slideInLeft');
      }
    }, 1000);
  }

  mostraIconCitazioneInCard(){
    setTimeout(() => {
      if (this.iconCitazioneContainer && this.iconCitazioneContainer.nativeElement.classList.contains('elementor-invisible')) {
        this.iconCitazioneContainer.nativeElement.classList.remove('elementor-invisible');
        this.iconCitazioneContainer.nativeElement.classList.add('animated');
        this.iconCitazioneContainer.nativeElement.classList.add('slideInRight');
      }
    }, 1000);
  }

  mostraCitazioneDescriptionInCard(){
    setTimeout(() => {
      if (this.citazioneDescriptionContainer && this.citazioneDescriptionContainer.nativeElement.classList.contains('elementor-invisible')) {
        this.citazioneDescriptionContainer.nativeElement.classList.remove('elementor-invisible');
        this.citazioneDescriptionContainer.nativeElement.classList.add('animated');
        this.citazioneDescriptionContainer.nativeElement.classList.add('slideInLeft');
      }
    }, 1000);
  }

  mostraLastArticlesSection(){
    setTimeout(() => {
      if (this.lastArticlesSection && this.lastArticlesSection.nativeElement.classList.contains('elementor-invisible')) {
        this.lastArticlesSection.nativeElement.classList.remove('elementor-invisible');
        this.lastArticlesSection.nativeElement.classList.add('animated');
        this.lastArticlesSection.nativeElement.classList.add('fadeIn');
      }
    }, 1000);
  }

  mostraLastArticlesSectionParent(){
    setTimeout(() => {
      if (this.lastArticlesSectionParent && this.lastArticlesSectionParent.nativeElement.classList.contains('elementor-invisible')) {
        this.lastArticlesSectionParent.nativeElement.classList.remove('elementor-invisible');
        this.lastArticlesSectionParent.nativeElement.classList.add('animated');
        this.lastArticlesSectionParent.nativeElement.classList.add('fadeIn');
      }
    }, 1000);
  }

}
