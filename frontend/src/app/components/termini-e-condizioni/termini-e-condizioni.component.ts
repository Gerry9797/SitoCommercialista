import { Component, OnInit } from '@angular/core';
import { SITE_CONFIG } from 'src/app/app.config';

@Component({
  selector: 'app-termini-e-condizioni',
  templateUrl: './termini-e-condizioni.component.html',
  styleUrls: ['./termini-e-condizioni.component.css']
})
export class TerminiECondizioniComponent implements OnInit {

  datiPersonali = SITE_CONFIG.datiPersonali;

  constructor() { }

  ngOnInit(): void {
  }

}
