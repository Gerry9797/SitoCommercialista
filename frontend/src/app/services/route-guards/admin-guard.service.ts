import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { SITE_CONFIG } from 'src/app/app.config';

@Injectable({
  providedIn: 'root'
})
export class AdminGuardService {

  roles = SITE_CONFIG.settings.permissionRoles

  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    let canPass: boolean = false;
    if(this.tokenStorage.getToken()){
      let user = this.tokenStorage.getUser();
      if(user && user.roles && user.roles.length > 0 && user.roles.includes(this.roles.ADM)) {
        canPass = true;
      }
    }
    if(canPass){
      return true;
    }
    else {
      this.router.navigate(['/error'])
      return false;
    }
    
  }
}
