import { Component, OnInit } from '@angular/core';
import { IFileDownloadCard } from 'src/app/bricks/cards/file-download-card/file-download-card.component';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-ebook',
  templateUrl: './ebook.component.html',
  styleUrls: ['./ebook.component.css']
})
export class EbookComponent implements OnInit {

  ebooksCards: IFileDownloadCard[] = [];

  constructor(
    private utilityService: UtilityService
  ) { }

  ngOnInit(): void {
    this.utilityService.scrollToTop();
    
    this.loadMockEBooks();
  }

  loadMockEBooks() {
    this.ebooksCards = [
      {
        title: "Congedi e permessi dal lavoro per genitori lavoratori dipendenti",
        description: "Settore privato e pubblico",
        actionLabel: "Scarica il PDF",
        type: "pdf"
      },
      {
        title: "Bonus Nido",
        description: "Hai appena iscritto il tuo/a bambino/a all’asilo nido? Scopri la mini guida veloce e completa",
        actionLabel: "Scarica il PDF",
        type: "pdf"
      }
    ]
  }

}
