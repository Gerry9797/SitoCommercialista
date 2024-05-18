import { Component, Input, OnInit } from '@angular/core';
import { IListItem } from 'src/app/models/list-item.model';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-inner-menu-area-riservata',
  templateUrl: './inner-menu-area-riservata.component.html',
  styleUrl: './inner-menu-area-riservata.component.css'
})
export class InnerMenuAreaRiservataComponent implements OnInit {

  @Input() activeItem : string | undefined = ""; 

  menuRiservatoItems: IListItem[] = [];

  constructor(
    private utilityService: UtilityService
  ) {}
  
  ngOnInit(): void {
    this.loadAreaRiservataMenu();
    this.setActiveItem();
  }

  loadAreaRiservataMenu() {
    this.menuRiservatoItems = [
      {
        label: "Bacheca",
        href: "/area-riservata/bacheca",
        activeItemKey: "bacheca"
      },
      {
        label: "Gestisci Appuntamenti",
        href: "/area-riservata/gestisci-appuntamento",
        activeItemKey: "gestisci-appuntamento"
      },
      {
        label: "Ordini",
        href: "/area-riservata/ordini/",
        activeItemKey: "ordini"
      },
      {
        label: "Indirizzi",
        href: "/area-riservata/indirizzi/",
        activeItemKey: "indirizzi"
      },
      {
        label: "Dettagli Account",
        href: "/area-riservata/dettaglio-account",
        activeItemKey: "dettagli-account"
      },
      {
        label: "Esci",
        href: "/area-riservata/logout/",
        activeItemKey: "logout"
      }
    ]
  }

  setActiveItem() {
    this.activeItem = this.menuRiservatoItems[0].activeItemKey;
  }

  logout(): void {
    this.utilityService.logout();
    // this.closeMobileMenu();
  }
  
}
