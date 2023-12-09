import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-privati',
  templateUrl: './privati.component.html',
  styleUrls: ['./privati.component.css']
})
export class PrivatiComponent implements OnInit {

  @ViewChild('gestioneCollaboratoriDomesticiContainer', { static: false }) gestioneCollaboratoriDomesticiContainer!: ElementRef;
  @ViewChild('imgGestioneCollaboratoriDomestici', { static: false }) imgGestioneCollaboratoriDomestici!: ElementRef;
  @ViewChild('gestioneCollaboratoriDomesticiDescription', { static: false }) gestioneCollaboratoriDomesticiDescription!: ElementRef;
  @ViewChild('prenotaConsulenzaBtnGestioneCollab', { static: false }) prenotaConsulenzaBtnGestioneCollab!: ElementRef;

  constructor() { }

  ngOnInit(): void {
    this.handleAnimations();
  }

  handleAnimations(){
    this.mostraGestioneCollaboratoriDomesticiContainer();
    this.mostraImgGestioneCollaboratoriDomestici();
    this.mostraGestioneCollaboratoriDomesticiDescription();
    this.mostraBtnPrenotaConsulenzaGestioneCollab();
  }

  mostraGestioneCollaboratoriDomesticiContainer(){
    setTimeout(() => {
      if (this.gestioneCollaboratoriDomesticiContainer && this.gestioneCollaboratoriDomesticiContainer.nativeElement.classList.contains('elementor-invisible')) {
        this.gestioneCollaboratoriDomesticiContainer.nativeElement.classList.remove('elementor-invisible');
        this.gestioneCollaboratoriDomesticiContainer.nativeElement.classList.add('animated');
        this.gestioneCollaboratoriDomesticiContainer.nativeElement.classList.add('zoomIn');
      }
    }, 1000);
  }

  mostraImgGestioneCollaboratoriDomestici(){
    setTimeout(() => {
      if (this.imgGestioneCollaboratoriDomestici && this.imgGestioneCollaboratoriDomestici.nativeElement.classList.contains('elementor-invisible')) {
        this.imgGestioneCollaboratoriDomestici.nativeElement.classList.remove('elementor-invisible');
        this.imgGestioneCollaboratoriDomestici.nativeElement.classList.add('animated');
        this.imgGestioneCollaboratoriDomestici.nativeElement.classList.add('slideInLeft');
      }
    }, 1000);
  }

  mostraGestioneCollaboratoriDomesticiDescription(){
    setTimeout(() => {
      if (this.gestioneCollaboratoriDomesticiDescription && this.gestioneCollaboratoriDomesticiDescription.nativeElement.classList.contains('elementor-invisible')) {
        this.gestioneCollaboratoriDomesticiDescription.nativeElement.classList.remove('elementor-invisible');
        this.gestioneCollaboratoriDomesticiDescription.nativeElement.classList.add('animated');
        this.gestioneCollaboratoriDomesticiDescription.nativeElement.classList.add('slideInLeft');
      }
    }, 1000);
  }

  mostraBtnPrenotaConsulenzaGestioneCollab(){
    setTimeout(() => {
      if (this.prenotaConsulenzaBtnGestioneCollab && this.prenotaConsulenzaBtnGestioneCollab.nativeElement.classList.contains('elementor-invisible')) {
        this.prenotaConsulenzaBtnGestioneCollab.nativeElement.classList.remove('elementor-invisible');
        this.prenotaConsulenzaBtnGestioneCollab.nativeElement.classList.add('animated');
        this.prenotaConsulenzaBtnGestioneCollab.nativeElement.classList.add('tada');
      }
    }, 1000);
  }

}
