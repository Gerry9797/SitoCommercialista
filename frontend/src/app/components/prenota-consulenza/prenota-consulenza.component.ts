import { Component, OnInit } from '@angular/core';
import { timeout } from 'rxjs';
import { NgxPopperjsTriggers, NgxPopperjsPlacements } from 'ngx-popperjs';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UtilityService } from '../../services/utility/utility.service';

interface City {
  name: string;
  code: string;
}

@Component({
  selector: 'app-prenota-consulenza',
  templateUrl: './prenota-consulenza.component.html',
  styleUrl: './prenota-consulenza.component.css'
})
export class PrenotaConsulenzaComponent implements OnInit {

    calendarLoading: boolean = false;
    calendarReady: boolean = false;
    calendarDaySelected: number|undefined = undefined;

    formCalendar!: FormGroup;

    // popper imports:
    positionPopper = NgxPopperjsPlacements.BOTTOM;
    triggerPopper = NgxPopperjsTriggers.click;

    mesi: string[] = [
      'Settembre',
      'Ottobre',
      'Novembre'
    ]

    selectedMese = this.mesi[0];

    constructor(
      private formBuilder: FormBuilder,
      private utilityService: UtilityService){
      
    }

    ngOnInit(): void {
      
      this.formCalendar = this.formBuilder.group({
        // signup_email: new FormControl(null, [Validators.required, Validators.email, Validators.maxLength(this.MAX_EMAIL_LEN)]),
        mese: new FormControl(null),
        anno: new FormControl(null)
      });

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
