import { HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor(
    private tokenStorage: TokenStorageService
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
    window.location.reload();
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


}