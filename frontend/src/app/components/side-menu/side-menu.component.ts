import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css']
})
export class SideMenuComponent implements OnInit, AfterViewInit {


  @ViewChild('buttonBounceIn', { static: false }) buttonBounceIn!: ElementRef;
  @ViewChild('sideMenuContainer', { static: false }) sideMenuContainer!: ElementRef;
  constructor() { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {

    this.mostraPulsantePrenotaConsulenzaQuandoNelloSchermo()
  
  }

  mostraPulsantePrenotaConsulenzaQuandoNelloSchermo(){
    setTimeout(() => {
      if (this.buttonBounceIn && this.buttonBounceIn.nativeElement.classList.contains('elementor-invisible')) {
        this.buttonBounceIn.nativeElement.classList.remove('elementor-invisible');
        this.buttonBounceIn.nativeElement.classList.add('animated');
        this.buttonBounceIn.nativeElement.classList.add('bounceIn');
      }
    }, 1000);
  }

  closeSideMenu(){
    this.sideMenuContainer.nativeElement.style.display = "none";
  }

}
