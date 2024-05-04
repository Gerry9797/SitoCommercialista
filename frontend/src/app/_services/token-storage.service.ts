import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { SITE_CONFIG } from '../app.config';
import { isPlatformBrowser } from '@angular/common';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
  ) { }

  signOut(): void {
    if (isPlatformBrowser(this.platformId)) {
      window.sessionStorage.clear();
    }
  }

  public saveToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      window.sessionStorage.removeItem(TOKEN_KEY);
      window.sessionStorage.setItem(TOKEN_KEY, token);
    }
  }

  public getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return window.sessionStorage.getItem(TOKEN_KEY);
    }
    else {
      return null
    }
  }

  public saveUser(user: any): void {
    if (isPlatformBrowser(this.platformId)) {
      window.sessionStorage.removeItem(USER_KEY);
      let userEnc = (SITE_CONFIG.settings.security.encodeSessionValues) ? window.btoa(JSON.stringify(user)) : JSON.stringify(user);
      window.sessionStorage.setItem(USER_KEY, userEnc);
    }
  }

  public getUser(): any {
    if (isPlatformBrowser(this.platformId)) {
      const user = window.sessionStorage.getItem(USER_KEY);
      if (user) {
        let userDec= (SITE_CONFIG.settings.security.encodeSessionValues) ? JSON.parse(window.atob(user)) : JSON.parse(user);
        // console.log(userDec)
        return userDec
      }

      return {};
    }
  }
}
