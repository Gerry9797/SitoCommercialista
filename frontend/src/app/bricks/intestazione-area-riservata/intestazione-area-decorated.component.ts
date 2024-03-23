import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-intestazione-area-decorated',
  templateUrl: './intestazione-area-decorated.component.html',
  styleUrl: './intestazione-area-decorated.component.css'
})
export class IntestazioneAreaDecoratedComponent {

  @Input() title: string = "title";
  @Input() subtitle: string | null = null;
}
