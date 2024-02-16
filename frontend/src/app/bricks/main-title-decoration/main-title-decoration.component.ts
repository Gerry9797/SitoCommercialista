import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-main-title-decoration',
  templateUrl: './main-title-decoration.component.html',
  styleUrl: './main-title-decoration.component.css'
})
export class MainTitleDecorationComponent {
  @Input() title = "title";
  @Input() subtitle = "subtitle";
}
