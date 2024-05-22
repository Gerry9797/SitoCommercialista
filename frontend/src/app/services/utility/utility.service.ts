import { HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

}
