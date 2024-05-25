import { HttpHeaders, HttpParams } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { SITE_CONFIG } from 'src/app/app.config';
import { InternalSessionManagerService } from '../session/internal-session-manager.service';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router,
    private internalSessionManager: InternalSessionManagerService
  ) { }

  ConfirmedValidator(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {

      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (
        matchingControl.errors &&
        !matchingControl.errors.confirmedValidator
      ) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ confirmedValidator: true });
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  logout(): void {
    this.tokenStorage.signOut();
    this.internalSessionManager.setRefreshAfterLoginLogout();
    this.router.navigate(['/autenticazione'])
    // window.location.reload();
    // setTimeout(() => {
    //   this.notifyService.showInfo("Logout effettuato");
    // }, 1000);
    
  }

  logoutWithoutRefresh(){
    this.tokenStorage.signOut();
  }

  scrollToTop() {
    window.scrollTo({top: 0, behavior: 'smooth'});
  }

  scrollToTopInstant() {
    window.scrollTo({top: 0 });
  }

  raiseScrollEvent(){
    window.scrollTo({top: 1, behavior: 'smooth'});
  }

  // addLangParams(params: HttpParams, httpOptions: any){
  //   params = params.set("lang", this.translateService.currentLang)
  //   let httpOptionsWithLang = {
  //     ...httpOptions,
  //     params
  //   }
  //   return httpOptionsWithLang;
  // }

  checkIfMobile() {
    // Ottieni la larghezza della finestra
    const windowWidth = window.innerWidth;

    // Imposta la variabile isMobile in base alla larghezza della finestra
    let isMobile = windowWidth <= 768; // Modifica il valore 768 con la larghezza desiderata per il tuo breakpoint mobile
    return isMobile;
  }

  getIndirizzoCompleto() : string {
    return SITE_CONFIG.datiPersonali.sede.indirizzo
      + " " + SITE_CONFIG.datiPersonali.sede.cap
      + " " + SITE_CONFIG.datiPersonali.sede.localita
      + " (" + SITE_CONFIG.datiPersonali.sede.provincia + ")"
  }

  scrollToElementIfNotInView(targetElement: ElementRef) {
    let element = targetElement.nativeElement;
    let rect = element.getBoundingClientRect();

    // Verifica se l'elemento è visibile nella finestra
    let isInView = rect.top >= 0 && rect.left >= 0 &&
      rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
      rect.right <= (window.innerWidth || document.documentElement.clientWidth);

    if (!isInView) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }

  scrollToElementIfNotInViewExcludingHeader(idElemTarget: string) {
    debugger
    let element = document.getElementById(idElemTarget);
    if (element) {
      let rect = element.getBoundingClientRect();
      let margin = 100; // Margine di 20 pixel sotto la parte superiore della finestra
  
      // Verifica se l'elemento è visibile nella finestra con il margine
      let isInView = rect.top >= margin && rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        rect.right <= (window.innerWidth || document.documentElement.clientWidth);
  
      if (!isInView) {
        window.scrollTo({
          top: window.scrollY + rect.top - margin,
          behavior: 'smooth'
        });
      }
    }

  }

}
