import {
  AfterViewInit,
  Component,
  ElementRef,
  Inject,
  OnInit,
  PLATFORM_ID,
  ViewChild,
} from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit, AfterViewInit {

  constructor(
    private utilityService: UtilityService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {

    this.utilityService.scrollToTop();
  }

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.smoothScrollingToAnchor();
    }
  }

  // SMOOTH SCROLLING TO ANCHOR
  smoothScrollingToAnchor() {
    document.querySelectorAll('.aSmoothScroll').forEach((anchor) => {
      anchor.addEventListener('click', function (e) {
        e.preventDefault();

        let anchor: any = e.target;
        if (anchor && anchor.hash) {
          let href: any = anchor.hash;
          // Recupera l'elemento di destinazione
          const element = document.querySelector(href);
          if (element) {
            // Calcola l'offset in modo personalizzato (50px in meno rispetto al top dell'elemento)
            const offset =
              element.getBoundingClientRect().top + window.scrollY - 150;

            // Effettua lo scroll con l'offset corretto
            window.scrollTo({
              top: offset,
              behavior: 'smooth',
            });
          }
        }
      });
    });
  }
}
