import { Component, Input } from '@angular/core';

export interface IFileDownloadCard {
  title: string;
  description: string;
  type?: "pdf";
  actionLabel: string;
}

@Component({
  selector: 'app-file-download-card',
  templateUrl: './file-download-card.component.html',
  styleUrl: './file-download-card.component.css'
})
export class FileDownloadCardComponent {

  @Input() cardDownloadDetail: IFileDownloadCard = {
    title: "title",
    description: "description",
    type: "pdf",
    actionLabel: "Scarica il PDF"
  };
}
