import { Component } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';

@Component({
  selector: 'app-presentation',
  templateUrl: './presentation.component.html',
  styleUrl: './presentation.component.css'
})
export class PresentationComponent {

  revisoreLegaleFlag: boolean = SITE_CONFIG.revisoreLegale

}
