import { Component, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isScrolled: boolean = false;

  constructor() { }

  ngOnInit(): void {

    this.handleListItemWithDropdownKeepSelectionOnItsUlChildren();
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

    // ul.addEventListener('mouseenter', () => {
    //   // Applica lo stile quando l'utente fa l'hover sul <ul>
    //   a.style.backgroundColor = 'black';
    // });

    // ul.addEventListener('mouseleave', () => {
    //   // Rimuovi lo stile quando l'utente esce dall'hover sul <ul>
    //   a.style.backgroundColor = ''; // Torna al valore predefinito
    // });
  }

  openSearchForm(){
    let searchFormList = document.querySelectorAll(".elementor-search-form__container");
    if(searchFormList){
      searchFormList.forEach((searchForm) => {
        searchForm.classList.add("elementor-search-form--full-screen");
        searchForm.classList.add("elementor-lightbox");
        document.getElementById("elementor-search-form-d0b0bb1")?.focus();
      })
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
    let cartToggleList = document.querySelectorAll(".elementor-widget-woocommerce-menu-cart");
    cartToggleList.forEach((cartToggle) => {
      cartToggle?.classList.add("elementor-menu-cart--shown")
    });
  }

  checkIfCloseCartDetail(event: any){
    let targetClicked = event.target;
    if(targetClicked.classList.contains("no-close-cart-details")){
      return
    }
    else{
      let cartToggleList = document.querySelectorAll(".elementor-widget-woocommerce-menu-cart");
      cartToggleList.forEach((cartToggle) => {
        cartToggle?.classList.remove("elementor-menu-cart--shown")
      });
    }

  }

  openSideMenu(){
    let sideMenuContainer = document.getElementById('side-menu-container');
    if(sideMenuContainer){
      sideMenuContainer.style.display = 'block';

      //fai comparire anche il pulsante così da gestirne l'animazione a partire da quando compare sullo shermo (sempre presente) e quando si apre come condizione che va ad aggiungere questa riga
      let sideMenuPrenotaBtn = document.getElementById('side-menu-prenota-btn');
      if(sideMenuPrenotaBtn){
        sideMenuPrenotaBtn.style.display = 'block';
      }
    }
  }


}
