import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { SITE_CONFIG } from 'src/app/app.config';
import { UtilityService } from 'src/app/services/utility/utility.service';
import { LottieComponent, AnimationOptions } from 'ngx-lottie';

@Component({
  selector: 'app-contatti',
  templateUrl: './contatti.component.html',
  styleUrls: ['./contatti.component.css']
})
export class ContattiComponent implements OnInit {

  lottieOptions: any;

  pinkMap: boolean = true;

  indirizzo!: string;
  // indirizzoGoogleMapsQueryParam!: string;

  telefono: string = SITE_CONFIG.datiPersonali.contatti.telefono;

  constructor(
    private utilityService: UtilityService,
    private sanitizer: DomSanitizer
  ) { 

    this.utilityService.scrollToTop();

    this.indirizzo = this.utilityService.getIndirizzoCompleto();
    // this.indirizzoGoogleMapsQueryParam = this.getQueryParamIndirizzoForGoogleMaps(this.indirizzo);
    // console.log(this.indirizzo);
    // console.log(this.indirizzoGoogleMapsQueryParam);

    this.lottieOptions = {
      path: '../../../assets/lottie/lottyEnvelope.json', // Inserisci il percorso del tuo file JSON
      renderer: 'svg',
      loop: true,
      autoplay: true
    };
  }

  ngOnInit(): void {
  }

  // getQueryParamIndirizzoForGoogleMaps(indirizzo: string) : string {
  //   return this.indirizzo.replace(/ /g, "%20").replace(/\(/g, "%28").replace(/\)/g, "%29");
  // }


}
