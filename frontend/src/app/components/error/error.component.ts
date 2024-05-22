import { Component, OnInit } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  supportEmail = SITE_CONFIG.support.email

  constructor() { }

  ngOnInit() {
  }

}
