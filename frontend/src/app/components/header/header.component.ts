import { isPlatformBrowser } from '@angular/common';
import { Component, OnInit, HostListener, Renderer2, Inject, PLATFORM_ID } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { SITE_CONFIG } from 'src/app/app.config';
import { InternalSessionManagerService } from 'src/app/services/session/internal-session-manager.service';
import { SideMenuManagerService } from 'src/app/services/side-menu-manager.service';

interface IMenuItem {
  redirectUrl?: string,  //campo li semplice
  label: string, //campo generale
  type: string, //campo generale
  children?: IMenuItemsChildren[], //campo li dropdown
  idToggleDropdown?: string, //campo dropdown
  idAriaControls?: string, //campo dropdown
}

interface IMenuItemsChildren {
  redirectUrl: string,
  label: string
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isScrolled: boolean = false;

  isHome: boolean = true;

  menuItems: IMenuItem[] = []

  cartItemsNum: number = 0;

  datiPersonali: any = SITE_CONFIG.datiPersonali

  isLoggedIn: boolean = false;
  roles: string[] = []; 

  constructor(
    private renderer: Renderer2,
    private sideMenuManager: SideMenuManagerService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object,
    private tokenStorage: TokenStorageService,
    private internalSessionManager: InternalSessionManagerService
  ) {
   }

  ngOnInit(): void {

    if (isPlatformBrowser(this.platformId)) {

      this.checkLogin();
      this.internalSessionManager.getObservableForRefreshAfterLoginLogout().subscribe(
        () => {
          this.checkLogin();
        }
      )

      this.loadMenuItemsConfiguration();

      this.handleListItemWithDropdownKeepSelectionOnItsUlChildren();
  
      this.checkIfIsHome();
    }

  }

  checkLogin(){
    
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
      console.log(this.roles)
    }
    else {
      this.isLoggedIn = false;
      this.roles = [];
    }
  }

  @HostListener('window:scroll', ['$event'])
  onWindowScroll(){
    //Verifica la posizione dello scroll
    if(window.pageYOffset !== 0){
      this.isScrolled = true;
    }
    else {
      this.isScrolled = false;
    }
  }

  checkIfIsHome(){
    // Sottoscrizione agli eventi di navigazione per controllare la rotta corrente
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Verifica se la rotta corrente è la home
        this.isHome = this.router.url === '/';
      }
    });
  }

  loadMenuItemsConfiguration(){
    this.menuItems = [
      {
        redirectUrl: "/chi-sono",
        label: "Chi sono",
        type: "simple"
      },
      {
        label: "Servizi",
        type: "dropdown",
        idToggleDropdown: "sm-16959237626095222-1",
        idAriaControls: "sm-16959237626095222-2",
        children: [
          {
            redirectUrl: "/aziende",
            label: "Aziende",
          },
          {
            redirectUrl: "/privati",
            label: "Privati",
          }
        ]
      },
      {
        redirectUrl: "/ebook-e-guide",
        label: "eBook",
        type: "simple"
      },
      {
        redirectUrl: "/webinar",
        label: "Webinar",
        type: "simple"
      },
      {
        redirectUrl: "/articoli",
        label: "Articoli",
        type: "simple"
      },
      {
        redirectUrl: "/contatti",
        label: "Contatti",
        type: "simple"
      }
    ]
  }

  handleListItemWithDropdownKeepSelectionOnItsUlChildren(){
    const ulList = document.querySelectorAll('li.open-dropdown-delayed ul');
    const aList = document.querySelectorAll('li.open-dropdown-delayed a.has-submenu');

    ulList.forEach(
      (ul) => {
        ul.addEventListener('mouseenter', () => {
          // Applica lo stile quando l'utente fa l'hover sul <ul>
          let aFratello = ul.parentElement?.querySelector("a");
          if(aFratello){
            aFratello.classList.add("highlighted");
          }
          
        });

        ul.addEventListener('mouseleave', () => {
          // Rimuovi lo stile quando l'utente esce dall'hover sul <ul>
          let aFratello = ul.parentElement?.querySelector("a");
          if(aFratello){
            aFratello.classList.remove("highlighted");
          }
        });
      }
    )
  }

  openSearchForm(){
    let searchForm = document.querySelector(".elementor-search-form__container");
    if(searchForm){
        searchForm.classList.add("elementor-search-form--full-screen");
        searchForm.classList.add("elementor-lightbox");
        document.getElementById("elementor-search-form-input-field")?.focus();
    }
    
  }

  checkIfCloseSearchForm(event: any){
    let targetClicked = event.target;
    if(!targetClicked.classList.contains("elementor-search-form__input")){
      let searchFormList = document.querySelectorAll(".elementor-search-form__container");
      if(searchFormList){
        searchFormList.forEach((searchForm) => {
          searchForm.classList.remove("elementor-search-form--full-screen")
          searchForm.classList.remove("elementor-lightbox")
        })
      }    
    }

  }

  openCartDetail(){
    let cart = document.querySelector(".elementor-menu-cart__container");
    cart?.classList.add("elementor-menu-cart--shown");
  }

  openSideMenu(){
    this.sideMenuManager.openSideMenu();
  }

  openModalPrenotazioneConsulenza(){
    
  }
}
