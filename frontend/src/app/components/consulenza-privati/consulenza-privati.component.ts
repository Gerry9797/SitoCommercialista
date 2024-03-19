import { Component, OnInit } from '@angular/core';
import { ConsulenzaCardModel } from 'src/app/models/consulenza-card.model';

@Component({
  selector: 'app-consulenza-privati',
  templateUrl: './consulenza-privati.component.html',
  styleUrl: './consulenza-privati.component.css'
})
export class ConsulenzaPrivatiComponent implements OnInit{

  consulenzaPrivatiCards: ConsulenzaCardModel[] = [];
  
  ngOnInit(): void {
    this.loadMockCardsConsulenzaPrivati();
  }

  loadMockCardsConsulenzaPrivati(){
    this.consulenzaPrivatiCards = [
      {
        title: "Consulenza Lavoratori",
        subtitle: "Rapporto di lavoro e diritto del lavoro.",
        description: "Hai in essere un contratto di lavoro dipendente e vorresti conoscere quali sono i diritti, doveri, obblighi e divieti previsti per la tua posizione attuale? Attraverso questa consulenza online potremmo affrontare i diversi aspetti del tuo lavoro assieme.",
        imgUrl: "assets/svg/consulenza_lavoratori.svg",
        price: "80,00 €",
        time: "50 min",
        redirectUrl: "/privati/consulenza/consulenza-lavoratori/"
      },
      {
        title: "Dimissioni e periodo di preavviso",
        subtitle: "Con o senza invio telematico",
        description: "Hai deciso di dimetterti e non sai qual è la procedura da seguire e quanto preavviso devi riconoscere al tuo datore di lavoro? Attraverso questa consulenza online vedremo quale è la procedura da seguire per effettuare le tue dimissioni e verificheremo il periodo di preavviso. Inoltre, a seconda della tua posizione lavorativa e personale, verificheremo se le dimissioni sono volontarie o per giusta causa, se vanno effettuate telematicamente o se hanno necessità di essere convalidate all'Ispettorato del Lavoro.",
        imgUrl: "assets/svg/consulenza_dimissioni_preavviso.svg",
        price: "50,00 €",
        time: "80 min",
        redirectUrl: "/privati/consulenza/dimissioni-e-periodo-di-preavviso"
      },
      {
        title: "Consulenza Genitori",
        subtitle: "Tutele in ambito genitorialità",
        description: "Sei un neo genitore o lo diventerai a breve? Vorresti conoscere quali sono le tutele previste in ambito di genitorialità e lavoro? Attraverso questa consulenza online ti spiegherò cosa prevede la normativa e come poter utilizzare gli strumenti a tua disposizione per conciliare vita e lavoro.",
        imgUrl: "assets/svg/consulenza_genitori.svg",
        price: "50,00 €",
        time: "80 min",
        redirectUrl: "/privati/consulenza/dimissioni-e-periodo-di-preavviso"
      },
      {
        title: "Assunzione collaboratore domestico",
        subtitle: "Colf, bandanti, baby sitter...",
        description: "Vorresti assumere un collaboratore domestico ma non sai cosa fare? Attraverso questa consulenza online ti indicherò cosa prevede la normativa italiana, quali sono gli adempimenti mensili, trimestrali, e annuali da mettere in atto e infine ti rilascerò un prospetto di costo relativo alla posizione individuata durante la consulenza online ai fini della assunzione del collaboratore domestico. Valuteremo inoltre se attivare un rapporto di collaborazione domestico oppure il libretto famiglia.",
        imgUrl: "assets/svg/consulenza_assunzione_collaboratore.svg",
        price: "120,00 €",
        time: "50 min",
        redirectUrl: "/privati/consulenza/assunzione-collaboratore-domestico"
      },
      {
        title: "Impariamo a leggere la tua busta paga",
        description: "Ti insegnerò a leggere la busta paga partendo dalla analisi della TUA busta paga. Analizzeremo nel dettaglio la busta paga nelle sue tre parti principali: Testa del cedolino, Corpo del cedolino, Competenze e trattenute (fiscali e previdenziali). Vedremo insieme il passaggio dalla retribuzione lorda a quella netta, attraverso l’analisi delle varie voci contenute nel cedolino (retribuzione, contributi INPS, enti assistenziali e tasse). Dopo l’incontro sarai in grado di poter leggere anche buste paghe diverse dalla tua. Tornerà utile ad esempio, se cambierai, in futuro, datore di lavoro. Attenzione: durante la consulenza online non verrà analizzata la correttezza di quanto riportato sulla busta paga rispetto alle previsioni normative e contrattuali. Questo tipo di analisi richiede infatti una consulenza mirata e più approfondita che potrai richiedere anche successivamente e per cui verrà predisposto eventualmente uno specifico preventivo.",
        imgUrl: "assets/svg/consulenza_lettura_busta_paga.svg",
        price: "80,00 €",
        time: "50 min",
        redirectUrl: "/privati/consulenza/impariamo-a-leggere-la-tua-busta-paga"
      }
    ]
  }

}