import { AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';
import { IListItem } from 'src/app/models/list-item.model';
import { SideMenuManagerService } from 'src/app/services/side-menu-manager.service';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css']
})
export class SideMenuComponent implements OnInit, AfterViewInit {

  datiPersonali: any = SITE_CONFIG.datiPersonali

  @ViewChild('sideMenuContainer', { static: false }) sideMenuContainer!: ElementRef;
  @ViewChild('sideMenuPrenotaBtn', { static: false }) sideMenuPrenotaBtn!: ElementRef;

  @ViewChild('sidemenu', { static: false }) sidemenuBody!: ElementRef;

  menuItems: IListItem[] = [];

  constructor(
    private sideMenuManager: SideMenuManagerService
  ) { }

  ngOnInit(): void {
    this.loadMenu();
  }

  loadMenu() {
    this.menuItems = [
      {
        label: "Chi sono",
        href: "chi-sono"
      },
      {
        label: "Servizi per Privati",
        href: "privati"
      },
      {
        label: "Servizi per Aziende",
        href: "aziende"
      },
      {
        label: "eBook",
        href: "ebook-e-guide"
      },
      {
        label: "Webinar",
        href: "webinar"
      },
      {
        label: "Articoli",
        href: "articoli"
      },
      {
        label: "Contatti",
        href: "contatti"
      },
      {
        label: "Log in",
        href: "autenticazione"
      }
    ]
  }

  ngAfterViewInit(): void {
    this.sideMenuManager.initHandler(this.sideMenuContainer, this.sideMenuPrenotaBtn);
  }

  closeSideMenu(){
    this.sideMenuManager.closeSideMenu();
  }

  checkIfCloseSideMobileMenu(event: any){
    if (!this.sidemenuBody.nativeElement.contains(event.target)) {
      this.closeSideMenu();
    }
  }

}
