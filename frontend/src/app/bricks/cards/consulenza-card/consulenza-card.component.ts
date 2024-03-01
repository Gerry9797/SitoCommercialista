import { AfterContentChecked, AfterContentInit, AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ConsulenzaCardModel } from 'src/app/models/consulenza-card.model';

@Component({
  selector: 'app-consulenza-card',
  templateUrl: './consulenza-card.component.html',
  styleUrl: './consulenza-card.component.css'
})
export class ConsulenzaCardComponent implements OnInit, AfterViewInit {

  @Input() card!: ConsulenzaCardModel
  @Input() expanded: boolean = false;
  truncated: boolean = false;

  actionLabel: string = "Prenota consulenza";

  @ViewChild('descriptionBlock', { static: false }) descriptionBlock!: ElementRef;

  ngOnInit(): void {

  }

  ngAfterViewInit(): void {
    this.isTextTruncated();
  }

  isTextTruncated() {
    var pContent = this.descriptionBlock.nativeElement;
    if (pContent.scrollHeight > pContent.clientHeight) {
      this.truncated = true;
    }
  }
  checkTruncation() {
    var divContent = this.descriptionBlock.nativeElement.innerText.trim();
    var fullContent = this.card.description.trim();

    if (divContent !== fullContent) {
        this.truncated = true;
    }
  }
}
