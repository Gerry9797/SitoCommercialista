import { Directive, ElementRef, Renderer2, OnInit, OnDestroy, PLATFORM_ID, Inject } from '@angular/core';
import { WindowService } from './window.service';
import { Subscription } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

const PRECISION: number = 10;

@Directive({
  selector: '[appear]'
})
export class AppearDirective implements OnInit, OnDestroy {

  private onWindowScrollSubscription!: Subscription;
  disappearOnlyOneTime: boolean = true; //default  (se è true, una volta apparso non scomparirà anche se non più contenuto nello schermo, se messo a false invece ogni volta che comparirà nello schermo apparirà e quando non lo sarà scomaprirà)

  animationType: string = "zoomIn"; //default value  (il tipo di animazione, combaciante con la classe css dell'amimazione)
  animationDelay: number = 0; //default value (delay da quando compare nello schermo a quando effettivamente viene attivata l'animazione di comparsa)

  constructor(
    private el: ElementRef, private renderer: Renderer2,
    private windowService: WindowService,
    @Inject(PLATFORM_ID) private platformId: Object,
    ) { }

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.init();
    }
  }

  ngOnDestroy() {
    if (this.onWindowScrollSubscription) {
      this.onWindowScrollSubscription.unsubscribe();
    }
  }

  private init() {

    this.loadAnimationSettings();

    // Subscribe to window scroll event through the windowService
    this.onWindowScrollSubscription = this.windowService.onWindowScroll().subscribe(() => this.checkAppearance());
    // Initial state
    if(! this.el.nativeElement.classList.contains('elementor-invisible')){
      this.renderer.addClass(this.el.nativeElement, 'elementor-invisible');
    }
    
    // Initial appearance check
    setTimeout(() => {
      this.checkAppearance();
    }, 500);
    
  }

  private loadAnimationSettings(){
        // Ottieni il valore dell'attributo custom "data-settings"
        let dataSettingsValue = this.el.nativeElement.getAttribute('data-settings');
        // Se l'attributo è presente
        if (dataSettingsValue) {
          let jsonDataSetting = JSON.parse(dataSettingsValue)
          // Il valore di "animation"
          if(jsonDataSetting.animation){
            this.animationType = JSON.parse(dataSettingsValue).animation;
          }
          if(jsonDataSetting.delay){
            this.animationDelay = JSON.parse(dataSettingsValue).delay;
          }

          if(jsonDataSetting.disappearAgain){
            this.disappearOnlyOneTime = !jsonDataSetting.disappearAgain;
          }
        }
  }

  /**
   * Check the item visibility
   */
  private checkAppearance() {

    if (this.el && this.el.nativeElement) {
      let rect = this.el.nativeElement.getBoundingClientRect();

      if ((rect.top >= 0 && (window.innerHeight - rect.top) >= (-PRECISION)) || (rect.bottom >= 0 && (window.innerHeight - rect.bottom) >= (-PRECISION))) {
        this.isVisible();
      }
      else {
        this.isNotVisible();
      }
    }
  }

  /**
   * Is visible
   */
  private isVisible() {
    
    setTimeout(() => {
      
      this.renderer.addClass(this.el.nativeElement, 'animated');
      this.renderer.addClass(this.el.nativeElement, this.animationType);
      
      if (this.el.nativeElement.classList.contains('elementor-invisible')) {
        this.renderer.removeClass(this.el.nativeElement, 'elementor-invisible');
      }
    }, this.animationDelay);

  }

  /**
   * Is not visible
   */
  private isNotVisible() {
    if(this.disappearOnlyOneTime){
      return
    }
    else {
      // this.renderer.addClass(this.el.nativeElement, 'elementor-invisible');

      if (this.el.nativeElement.classList.contains(this.animationType)) {
        this.renderer.removeClass(this.el.nativeElement, 'animated');
        this.renderer.removeClass(this.el.nativeElement, this.animationType);
      }
    }

  }
}