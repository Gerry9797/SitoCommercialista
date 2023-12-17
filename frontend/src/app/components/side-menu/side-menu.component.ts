import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css']
})
export class SideMenuComponent implements OnInit, AfterViewInit {

  @ViewChild('sideMenuContainer', { static: false }) sideMenuContainer!: ElementRef;
  @ViewChild('sideMenuPrenotaBtn', { static: false }) sideMenuPrenotaBtn!: ElementRef;

  constructor() { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
  
  }

  closeSideMenu(){
    this.sideMenuContainer.nativeElement.style.display = "none";
    this.sideMenuPrenotaBtn.nativeElement.style.display = "none";
  }

}
