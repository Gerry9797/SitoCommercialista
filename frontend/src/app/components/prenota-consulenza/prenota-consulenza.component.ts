import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UtilityService } from '../../services/utility/utility.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MAP_MESI } from 'src/app/app.constants';
import { GiornoDelMese } from 'src/app/models/calendar-reservation.model';
import { NgScrollbar } from 'ngx-scrollbar';

interface LeftMenuItems {
  label: string;
  description?: string;
  iconClass: string;
  checked: boolean;
}
@Component({
  selector: 'app-prenota-consulenza',
  templateUrl: './prenota-consulenza.component.html',
  styleUrl: './prenota-consulenza.component.css',
  animations: [
    trigger('fadeOut', [
      state('visible', style({
        opacity: 1,
        display: 'block'
      })),
      state('hidden', style({
        opacity: 0,
        display: 'none'
      })),
      transition('visible => hidden', [
        animate('500ms ease-out')
      ]),
      transition('hidden => visible', [
        animate('500ms 600ms ease-in') // 600ms delay before becoming visible
      ])
    ])
  ]
})
export class PrenotaConsulenzaComponent implements OnInit {

    @ViewChild('calendarScrollbar') calendarScrollbarRef!: NgScrollbar;

    mapMesi = MAP_MESI;

    calendarLoading: boolean = false;
    calendarReady: boolean = false;

    isLeftMenuCalendarClosed: boolean = false;

    formCalendar!: FormGroup;



    leftMenuItems: LeftMenuItems[] = [
      {
        label: "Data e Orario",
        description: "1 febbraio 2024 - 15:00",
        iconClass: "am-icon-date-time",
        checked: false
      },
      {
        label: "Informazioni",
        iconClass: "am-icon-user",
        checked: false
      },
      {
        label: "Pagamenti",
        iconClass: "am-icon-payment",
        checked: false
      }
    ];

    mesi: string[] = [
      'Settembre',
      'Ottobre',
      'Novembre'
    ]

    anni: string[] = [
      '2024'
    ]

    giorniDelMese: GiornoDelMese[] = [
      {
        day: 1,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 2,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false,
        isToday: true
      },
      {
        day: 3,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 4,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 5,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 6,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 7,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 8,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 9,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 10,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 11,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: true,
        occupancyPercentage: 33,
        orariPrenotazione : [
          "15:00",
          "16:00",
          "17:00"
        ]
      },      
      {
        day: 12,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 13,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 14,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 15,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 16,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: true,
        occupancyPercentage: 33
      },      
      {
        day: 17,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 18,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: true,
        occupancyPercentage: 66,
        orariPrenotazione : [
          "15:00"
        ]
      },      
      {
        day: 19,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },      
      {
        day: 20,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 21,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 22,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 23,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 24,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 25,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: true,
        occupancyPercentage: 33
      },
      {
        day: 26,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 27,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 28,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 29,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 30,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: true,
        occupancyPercentage: 33
      },
      {
        day: 31,
        month: 8,
        year: 2024,
        monthState: 'CURRENT',
        enabled: false
      },
      {
        day: 1,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: true,
        occupancyPercentage: 33
      },
      {
        day: 2,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      },
      {
        day: 3,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      },
      {
        day: 4,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      },
      {
        day: 5,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      },      
      {
        day: 6,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      },      
      {
        day: 7,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      },      
      {
        day: 8,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: true,
        occupancyPercentage: 33
      },      
      {
        day: 9,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      },      
      {
        day: 10,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      },      
      {
        day: 11,
        month: 9,
        year: 2024,
        monthState: 'FUTURE',
        enabled: false
      }
    ]
    giorniDellaSettimana: string[] = [
      "lun", "mar", "mer", "gio", "ven", "sab", "dom"
    ]

    selectedMese = this.mesi[0];
    selectedAnno = this.anni[0];
    selectedGiornoDelMese: null | GiornoDelMese = null
    selectedOrario: null | string = null;
    
    step: number = 1;

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
      }, 2000);
      
    }

    toggleLeftMenuCalendar() {
      this.isLeftMenuCalendarClosed = !this.isLeftMenuCalendarClosed;
    }

    selectDay(day: GiornoDelMese) {
      this.selectedGiornoDelMese = day;
      let orariDisponibili = this.selectedGiornoDelMese.orariPrenotazione;
      if (orariDisponibili != null && orariDisponibili.length > 0) {
        this.selectedOrario = orariDisponibili[0];
      } else {
        this.selectedOrario = null;
      }
      setTimeout(() => {
        this.scrollToBottomOfCalendar();
      }, 200);
      
    }

    selectTime(time: string) {
      this.selectedOrario = time;
    }

    scrollToBottomOfCalendar() {
      if (this.calendarScrollbarRef) {
        // Scorrimento dolce verso il fondo
        this.calendarScrollbarRef.scrollTo({ bottom: 0, duration: 300 });  // Durata in millisecondi
      }
    }

    nextStep() {
      this.step += 1;
    }

    prevStep() {
      if (this.step > 1) {
        this.step -= 1;
      }
    }
}
