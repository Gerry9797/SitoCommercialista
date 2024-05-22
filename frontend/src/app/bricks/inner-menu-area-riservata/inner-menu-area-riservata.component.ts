import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    private utilityService: UtilityService,
    private activatedRoute: ActivatedRoute
  ) {}
  
  ngOnInit(): void {
    this.loadAreaRiservataMenu();
    this.handleSelectionItemMenu();
  }

  handleSelectionItemMenu() {
    let relativeUrl = "/" + this.activatedRoute.snapshot.url.join('/');
    let activeItemObj = this.menuRiservatoItems.find(item => item.href == relativeUrl)
    this.activeItem = activeItemObj?.activeItemKey;
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
        href: "/area-riservata/ordini",
        activeItemKey: "ordini"
      },
      {
        label: "Indirizzi",
        href: "/area-riservata/indirizzi",
        activeItemKey: "indirizzi"
      },
      {
        label: "Dettagli Account",
        href: "/area-riservata/dettaglio-account",
        activeItemKey: "dettagli-account"
      },
      {
        label: "Esci",
        href: "/area-riservata/logout",
        activeItemKey: "logout"
      }
    ]
  }

  setActiveItem(key: string | undefined) {
    if (key) {
      this.activeItem = key;
    }
    else {
      this.activeItem = this.menuRiservatoItems[0].activeItemKey;
    }
  }

  logout(): void {
    this.utilityService.logout();
    // this.closeMobileMenu();
  }
  
}
