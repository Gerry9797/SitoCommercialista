import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contatti',
  templateUrl: './contatti.component.html',
  styleUrls: ['./contatti.component.css']
})
export class ContattiComponent implements OnInit {

  lottieOptions: any;

  pinkMap: boolean = true;

  constructor() { 
    this.lottieOptions = {
      path: '../../../assets/lottie/lottyEnvelope.json', // Inserisci il percorso del tuo file JSON
      renderer: 'svg',
      loop: true,
      autoplay: true
    };
  }

  ngOnInit(): void {
  }

}
