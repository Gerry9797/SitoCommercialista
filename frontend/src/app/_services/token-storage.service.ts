import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { SITE_CONFIG } from '../app.config';
import { isPlatformBrowser } from '@angular/common';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  ENCODED_TOKEN_KEY = TOKEN_KEY;
  ENCODED_USER_KEY = USER_KEY;

  securitySetting = SITE_CONFIG.settings.security

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
  ) { 
    if(this.securitySetting.encodeSessionValues) {
      this.ENCODED_TOKEN_KEY = this.customEncode(TOKEN_KEY);
      this.ENCODED_USER_KEY = this.customEncode(USER_KEY);
    }
  }

  signOut(): void {
    if (isPlatformBrowser(this.platformId)) {
      window.sessionStorage.clear();
    }
  }

  public saveToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {

      if (this.securitySetting.encodeSessionValues) {
        token = this.customEncode(token)
      }

      window.sessionStorage.removeItem(this.ENCODED_TOKEN_KEY);
      window.sessionStorage.setItem(this.ENCODED_TOKEN_KEY, token);
    }
  }

  public getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      let token = window.sessionStorage.getItem(this.ENCODED_TOKEN_KEY);
      if (this.securitySetting.encodeSessionValues && token) {
        token = this.customDecode(token);
      }
      return token;
    }
    else {
      return null
    }
  }

  public saveUser(user: any): void {
    if (isPlatformBrowser(this.platformId)) {
      window.sessionStorage.removeItem(this.ENCODED_USER_KEY);
      let userEnc = (this.securitySetting.encodeSessionValues) ? this.customEncode(JSON.stringify(user)) : JSON.stringify(user);
      window.sessionStorage.setItem(this.ENCODED_USER_KEY, userEnc);
    }
  }

  public getUser(): any {
    if (isPlatformBrowser(this.platformId)) {
      const user = window.sessionStorage.getItem(this.ENCODED_USER_KEY);
      if (user) {
        let userDec= (this.securitySetting.encodeSessionValues) ? JSON.parse(this.customDecode(user)) : JSON.parse(user);
        // console.log(userDec)
        return userDec
      }

      return {};
    }
  }

  private customEncode(value: string) : string {
    return window.btoa(this.securitySetting.token1 + window.btoa(value)  + this.securitySetting.token2);
  }

  private customDecode(value: string) : string {
    let valueProcessed = window.atob(value);
    valueProcessed = valueProcessed.replace(this.securitySetting.token1, "").replace(this.securitySetting.token2, "")
    return window.atob(valueProcessed);
  }
}
