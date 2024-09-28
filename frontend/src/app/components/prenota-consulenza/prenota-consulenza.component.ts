import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UtilityService } from '../../services/utility/utility.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MAP_MESI } from 'src/app/app.constants';

interface City {
  name: string;
  code: string;
}

interface LeftMenuItems {
  label: string;
  description?: string;
  iconClass: string;
  checked: boolean;
}

interface GiorniDelMese {
  day: number,
  month: number,
  year: number
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

    mapMesi = MAP_MESI;

    calendarLoading: boolean = false;
    calendarReady: boolean = false;
    calendarDaySelected: number|undefined = undefined;
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

    giorniDelMese: GiorniDelMese[] = [
      {
        day: 1,
        month: 8,
        year: 2024
      },
      {
        day: 2,
        month: 8,
        year: 2024
      },
      {
        day: 3,
        month: 8,
        year: 2024
      },
      {
        day: 4,
        month: 8,
        year: 2024
      },
      {
        day: 5,
        month: 8,
        year: 2024
      },      
      {
        day: 6,
        month: 8,
        year: 2024
      },      
      {
        day: 7,
        month: 8,
        year: 2024
      },      
      {
        day: 8,
        month: 8,
        year: 2024
      },      
      {
        day: 9,
        month: 8,
        year: 2024
      },      
      {
        day: 10,
        month: 8,
        year: 2024
      },      
      {
        day: 11,
        month: 8,
        year: 2024
      },      
      {
        day: 12,
        month: 8,
        year: 2024
      },      
      {
        day: 13,
        month: 8,
        year: 2024
      },      
      {
        day: 14,
        month: 8,
        year: 2024
      },      
      {
        day: 15,
        month: 8,
        year: 2024
      },      
      {
        day: 16,
        month: 8,
        year: 2024
      },      
      {
        day: 17,
        month: 8,
        year: 2024
      },      
      {
        day: 18,
        month: 8,
        year: 2024
      },      
      {
        day: 19,
        month: 8,
        year: 2024
      },      
      {
        day: 20,
        month: 8,
        year: 2024
      },
      {
        day: 21,
        month: 8,
        year: 2024
      },
      {
        day: 22,
        month: 8,
        year: 2024
      },
      {
        day: 23,
        month: 8,
        year: 2024
      },
      {
        day: 24,
        month: 8,
        year: 2024
      },
      {
        day: 25,
        month: 8,
        year: 2024
      },
      {
        day: 26,
        month: 8,
        year: 2024
      },
      {
        day: 27,
        month: 8,
        year: 2024
      },
      {
        day: 28,
        month: 8,
        year: 2024
      },
      {
        day: 29,
        month: 8,
        year: 2024
      },
      {
        day: 30,
        month: 8,
        year: 2024
      },
      {
        day: 31,
        month: 8,
        year: 2024
      },
      {
        day: 1,
        month: 9,
        year: 2024
      },
      {
        day: 2,
        month: 9,
        year: 2024
      },
      {
        day: 3,
        month: 9,
        year: 2024
      },
      {
        day: 4,
        month: 9,
        year: 2024
      },
      {
        day: 5,
        month: 9,
        year: 2024
      },      
      {
        day: 6,
        month: 9,
        year: 2024
      },      
      {
        day: 7,
        month: 9,
        year: 2024
      },      
      {
        day: 6,
        month: 9,
        year: 2024
      },      
      {
        day: 9,
        month: 9,
        year: 2024
      },      
      {
        day: 10,
        month: 9,
        year: 2024
      },      
      {
        day: 11,
        month: 9,
        year: 2024
      }
    ]
    giorniDellaSettimana: string[] = [
      "lun", "mar", "mer", "gio", "ven", "sab", "dom"
    ]

    selectedMese = this.mesi[0];
    selectedAnno = this.anni[0];

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
}
