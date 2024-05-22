import { isPlatformBrowser } from '@angular/common';
import {
  AfterViewInit,
  Component,
  ElementRef,
  Inject,
  OnInit,
  PLATFORM_ID,
  Renderer2,
  ViewChild,
} from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { SITE_CONFIG } from 'src/app/app.config';
import { IListItem } from 'src/app/models/list-item.model';
import { InternalSessionManagerService } from 'src/app/services/session/internal-session-manager.service';
import { SideMenuManagerService } from 'src/app/services/side-menu-manager.service';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css'],
})
export class SideMenuComponent implements OnInit, AfterViewInit {
  datiPersonali: any = SITE_CONFIG.datiPersonali;

  @ViewChild('sideMenuContainer', { static: false })
  sideMenuContainer!: ElementRef;
  @ViewChild('sideMenuPrenotaBtn', { static: false })
  sideMenuPrenotaBtn!: ElementRef;

  @ViewChild('sidemenu', { static: false }) sidemenuBody!: ElementRef;

  menuItems: IListItem[] = [];
  isLoggedIn: boolean = false;
  roles: string[] = [];

  constructor(
    private sideMenuManager: SideMenuManagerService,
    private internalSessionManager: InternalSessionManagerService,
    private tokenStorage: TokenStorageService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    this.handleSideMenu();

    if (isPlatformBrowser(this.platformId)) {
      this.internalSessionManager
        .getObservableForRefreshAfterLoginLogout()
        .subscribe(() => {
          this.handleSideMenu();
        });
    }
  }

  handleSideMenu() {
    this.checkLogin();
    this.loadMenu();
  }

  checkLogin() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    } else {
      this.isLoggedIn = false;
      this.roles = [];
    }
  }

  loadMenu() {
    this.menuItems = [
      {
        label: 'Chi sono',
        href: 'chi-sono',
      },
      {
        label: 'Servizi per Privati',
        href: 'privati',
      },
      {
        label: 'Servizi per Aziende',
        href: 'aziende',
      },
      {
        label: 'eBook',
        href: 'ebook-e-guide',
      },
      {
        label: 'Webinar',
        href: 'webinar',
      },
      {
        label: 'Articoli',
        href: 'articoli',
      },
      {
        label: 'Contatti',
        href: 'contatti',
      },
    ];

    if (this.isLoggedIn) {
      this.menuItems.push({
        label: 'Log out',
        href: '/area-riservata/logout',
      });
    } else {
      this.menuItems.push({
        label: 'Log in',
        href: '/autenticazione',
      });
    }
  }

  ngAfterViewInit(): void {
    this.sideMenuManager.initHandler(
      this.sideMenuContainer,
      this.sideMenuPrenotaBtn
    );
  }

  closeSideMenu() {
    this.sideMenuManager.closeSideMenu();
  }

  checkIfCloseSideMobileMenu(event: any) {
    if (!this.sidemenuBody.nativeElement.contains(event.target)) {
      this.closeSideMenu();
    }
  }
}
