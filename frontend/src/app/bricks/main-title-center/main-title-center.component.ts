import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-main-title-center',
  templateUrl: './main-title-center.component.html',
  styleUrl: './main-title-center.component.css'
})
export class MainTitleCenterComponent {
  @Input() title: string = "Titolo Prova";
}
