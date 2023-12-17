import { ElementRef, Injectable, Renderer2, RendererFactory2 } from '@angular/core';
import { UtilityService } from './utility/utility.service';

@Injectable({
  providedIn: 'root'
})
export class SideMenuManagerService {

  sideMenuContainer!: ElementRef;
  sideMenuPrenotaBtn!: ElementRef;

  isMobile!: boolean;

  private renderer!: Renderer2;

  constructor(
    private rendererFactory: RendererFactory2,
    private utilityService: UtilityService
  ) { 
    this.renderer = rendererFactory.createRenderer(null, null);
    this.isMobile = this.utilityService.checkIfMobile();
  }
  
  initHandler(sideMenuContainer: ElementRef, sideMenuPrenotaBtn: ElementRef){
    this.setElementsRef(sideMenuContainer, sideMenuPrenotaBtn);
  }

  private setElementsRef(sideMenuContainer: ElementRef, sideMenuPrenotaBtn: ElementRef){
    this.sideMenuContainer = sideMenuContainer;
    this.sideMenuPrenotaBtn = sideMenuPrenotaBtn;
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

    //segnala sul body che il side menù si è aperto (fix per non permettere al body di scrollare su mobile se si apre il menu)
    let body = document.body;
    if(!body.classList.contains('sideMenuOpened')){
      let savedScrollPosition = window.scrollY;  //perchè la classe che applicheremo sul body rimuoverà la scollabilità sul body, facendoci perdere la scroll position
      this.renderer.setAttribute(document.body, 'data-saved-scroll-position', savedScrollPosition.toString()); //settato come data attribute
      body.classList.add('sideMenuOpened')
    }
  }

  closeSideMenu(){
    this.sideMenuContainer.nativeElement.style.display = "none";
    this.sideMenuPrenotaBtn.nativeElement.style.display = "none";

    //segnala sul body che il side menù si è chiuso (fix per non permettere al body di scrollare su mobile se si apre il menu)
    let body = document.body;
    if(body.classList.contains('sideMenuOpened')){
      let savedPosition = this.getSavedScrollPosition();
      this.renderer.removeAttribute(document.body, 'data-saved-scroll-position');
      body.classList.remove('sideMenuOpened')
      if(this.isMobile){
        window.scrollTo(0, savedPosition);
      }
    }
  }

  private getSavedScrollPosition() {
    let savedScrollPositionAttribute = document.body.getAttribute('data-saved-scroll-position');
    let savedScrollPosition = savedScrollPositionAttribute ? parseInt(savedScrollPositionAttribute, 10) : 0;
    return savedScrollPosition;
  }
}
