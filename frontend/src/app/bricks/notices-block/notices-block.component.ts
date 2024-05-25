import { Component, Input, OnInit } from '@angular/core';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-notices-block',
  templateUrl: './notices-block.component.html',
  styleUrls: ['./notices-block.component.css']
})
export class NoticesBlockComponent implements OnInit {

  @Input() message: string = "";
  @Input() title: string | undefined = undefined;
  @Input() status: string = "info";  // VALORI AMMESSI [error, info, message]
  @Input() idCustom: string = uuidv4();

  constructor() { }

  ngOnInit(): void {
  }

}
