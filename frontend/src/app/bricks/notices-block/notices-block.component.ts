import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-notices-block',
  templateUrl: './notices-block.component.html',
  styleUrls: ['./notices-block.component.css']
})
export class NoticesBlockComponent implements OnInit {

  @Input() message: string = "";
  @Input() status: string = "info";  // VALORI AMMESSI [error, info, message]

  constructor() { }

  ngOnInit(): void {
  }

}
