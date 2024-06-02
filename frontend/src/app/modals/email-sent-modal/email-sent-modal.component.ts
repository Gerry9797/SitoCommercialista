import { Component, OnInit } from '@angular/core';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SITE_CONFIG } from 'src/app/app.config';

@Component({
  selector: 'app-email-sent-modal',
  templateUrl: './email-sent-modal.component.html',
  styleUrls: ['./email-sent-modal.component.css'],
  providers:[DialogService]
})
export class EmailSentModalComponent implements OnInit {

  email: string = "";
  emailAssistenza: string = SITE_CONFIG.support.email;

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    public dialogService: DialogService
  ) { }

  ngOnInit(): void {
    this.email = this.config.data.email;
  }

  closeModal(){
    this.ref.close();
  }

}
