import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-macrocard-impression',
  templateUrl: './macrocard-impression.component.html',
  styleUrl: './macrocard-impression.component.css'
})
export class MacrocardImpressionComponent implements OnInit {

  @ViewChild('citazioneContainer', { static: false })
  citazioneContainer!: ElementRef;
  @ViewChild('iconCitazioneContainer', { static: false })
  iconCitazioneContainer!: ElementRef;
  @ViewChild('citazioneDescriptionContainer', { static: false })
  citazioneDescriptionContainer!: ElementRef;
  
  ngOnInit(): void {
    this.handleAnimations();
  }

  handleAnimations() {
    this.mostraCitazioneInCard();
    this.mostraIconCitazioneInCard();
    this.mostraCitazioneDescriptionInCard();
  }

  mostraCitazioneInCard() {
    setTimeout(() => {
      if (
        this.citazioneContainer &&
        this.citazioneContainer.nativeElement.classList.contains(
          'elementor-invisible'
        )
      ) {
        this.citazioneContainer.nativeElement.classList.remove(
          'elementor-invisible'
        );
        this.citazioneContainer.nativeElement.classList.add('animated');
        this.citazioneContainer.nativeElement.classList.add('slideInLeft');
      }
    }, 1000);
  }

  mostraIconCitazioneInCard() {
    setTimeout(() => {
      if (
        this.iconCitazioneContainer &&
        this.iconCitazioneContainer.nativeElement.classList.contains(
          'elementor-invisible'
        )
      ) {
        this.iconCitazioneContainer.nativeElement.classList.remove(
          'elementor-invisible'
        );
        this.iconCitazioneContainer.nativeElement.classList.add('animated');
        this.iconCitazioneContainer.nativeElement.classList.add('slideInRight');
      }
    }, 1000);
  }

  mostraCitazioneDescriptionInCard() {
    setTimeout(() => {
      if (
        this.citazioneDescriptionContainer &&
        this.citazioneDescriptionContainer.nativeElement.classList.contains(
          'elementor-invisible'
        )
      ) {
        this.citazioneDescriptionContainer.nativeElement.classList.remove(
          'elementor-invisible'
        );
        this.citazioneDescriptionContainer.nativeElement.classList.add(
          'animated'
        );
        this.citazioneDescriptionContainer.nativeElement.classList.add(
          'slideInLeft'
        );
      }
    }, 1000);
  }

}
