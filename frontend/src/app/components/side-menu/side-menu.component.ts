import { AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { SideMenuManagerService } from 'src/app/services/side-menu-manager.service';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css']
})
export class SideMenuComponent implements OnInit, AfterViewInit {

  @ViewChild('sideMenuContainer', { static: false }) sideMenuContainer!: ElementRef;
  @ViewChild('sideMenuPrenotaBtn', { static: false }) sideMenuPrenotaBtn!: ElementRef;

  constructor(
    private sideMenuManager: SideMenuManagerService
  ) { }

  ngOnInit(): void {
  
  }

  ngAfterViewInit(): void {
    this.sideMenuManager.initHandler(this.sideMenuContainer, this.sideMenuPrenotaBtn);
  }

  closeSideMenu(){
    this.sideMenuManager.closeSideMenu();
  }

}
