import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-main-title-center-decorated',
  templateUrl: './main-title-center-decorated.component.html',
  styleUrl: './main-title-center-decorated.component.css'
})
export class MainTitleCenterDecoratedComponent {
  @Input() title: string = "Titolo Prova";
}
