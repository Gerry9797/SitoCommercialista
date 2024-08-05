import { Component, OnInit } from '@angular/core';
import { timeout } from 'rxjs';

@Component({
  selector: 'app-prenota-consulenza',
  templateUrl: './prenota-consulenza.component.html',
  styleUrl: './prenota-consulenza.component.css'
})
export class PrenotaConsulenzaComponent implements OnInit {

    calendarLoading: boolean = false;
    calendarReady: boolean = false;

    ngOnInit(): void {
      setTimeout(() => {
        this.calendarLoading = true;
        this.loadCalendarConfiguration();
      }, 200);
      
    }

    loadCalendarConfiguration() {
      //carica qui la configurazione dei giorni/mesi dal backend
      setTimeout(() => {
        this.calendarReady = true;
      }, 3000);
      
    }
}
