import { Component } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {

    this.detectIfDeviceIsIphone();

    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  detectIfDeviceIsIphone(){
    document.addEventListener('DOMContentLoaded', function () {
      // Rileva se il dispositivo è un iPhone
      if (/iPhone/.test(navigator.userAgent)) {
        // Ottieni l'elemento HTML che desideri modificare (ad esempio, il corpo della pagina)
        var bodyElement = document.body;
        
        // Aggiungi la classe CSS solo se il dispositivo è un iPhone
        bodyElement.classList.add('iphone-class');
      }
    });
  }
}
