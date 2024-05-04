import { Inject, Injectable } from '@angular/core';
// import { SessionStorageService, LocalStorageService } from 'ngx-webstorage';
import { Subject } from 'rxjs';
// import { CookiesManagerService } from '../cookies/cookies-manager-service.service';

import { PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, isPlatformServer } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class InternalSessionManagerService {  //è questo che si interfaccia con il session storage

  // KEY_LANG = "lang"

  // ########### DETECT LOGIN/LOGOUT #########################
  private refreshAfterLoginLogout$: Subject<void> = new Subject();

  public getObservableForRefreshAfterLoginLogout(): Subject<void> {
    return this.refreshAfterLoginLogout$;
  }

  public setRefreshAfterLoginLogout(): void {
    this.refreshAfterLoginLogout$.next();
  }

// ############################################################

  //LANGUAGE SESSION
  // private langSession: string = LANGS.ENG;  // lingua corrente, prendo valore di default ma poi controllo se presente nel session storage, se c'è sostituisco

  // public getLangSession(): string {
  //   return this.langSession;
  // }

  // public setLangSession(lang: string) {
  //   this.langSession = lang
  //   this.localStorageService.store(this.KEY_LANG, lang)
  // }


  constructor(
    
    @Inject(PLATFORM_ID) private platformId: Object,
    // private sessionStorageService: SessionStorageService,
    // private localStorageService: LocalStorageService,
    // private cookieManagerService: CookiesManagerService
    ) {

    if (isPlatformBrowser(this.platformId)) {

      //############# recupera  da sessionStorage ##############
           
      // - LANG
      // let langStorage = this.localStorageService.retrieve(this.KEY_LANG)
      // if (langStorage && langStorage !== "") {
      //   this.langSession = langStorage;
      // }
    }
  }




}
