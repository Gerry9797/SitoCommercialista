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
  disappearOnlyOneTime: boolean = true;

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
    // Subscribe to window scroll event through the windowService
    this.onWindowScrollSubscription = this.windowService.onWindowScroll().subscribe(() => this.checkAppearance());
    // Initial state
    this.renderer.addClass(this.el.nativeElement, 'not-visible');
    // Initial appearance check
    this.checkAppearance();
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
    this.renderer.addClass(this.el.nativeElement, 'visible');

    if (this.el.nativeElement.classList.contains('not-visible')) {
      this.renderer.removeClass(this.el.nativeElement, 'not-visible');
    }
  }

  /**
   * Is not visible
   */
  private isNotVisible() {
    if(this.disappearOnlyOneTime){
      return
    }
    else {
      this.renderer.addClass(this.el.nativeElement, 'not-visible');

      if (this.el.nativeElement.classList.contains('visible')) {
        this.renderer.removeClass(this.el.nativeElement, 'visible');
      }
    }

  }
}