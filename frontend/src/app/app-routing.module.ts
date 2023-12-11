import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { AboutComponent } from './components/about/about.component';
import { ContattiComponent } from './components/contatti/contatti.component';
import { EbookComponent } from './components/ebook/ebook.component';
import { WebinarComponent } from './components/webinar/webinar.component';
import { ArticoliComponent } from './components/articoli/articoli.component';
import { AziendeComponent } from './components/aziende/aziende.component';
import { PrivatiComponent } from './components/privati/privati.component';
import { LoginSignupComponent } from './components/login-signup/login-signup.component';
import { PrivacyPolicyComponent } from './components/privacy-policy/privacy-policy.component';
import { CookiePolicyComponent } from './components/cookie-policy/cookie-policy.component';
import { TerminiECondizioniComponent } from './components/termini-e-condizioni/termini-e-condizioni.component';
import { RecuperaPasswordComponent } from './components/recupera-password/recupera-password.component';
import { BachecaComponent } from './components/bacheca/bacheca.component';
import { GestisciAppuntamentiComponent } from './components/gestisci-appuntamenti/gestisci-appuntamenti.component';
import { OrdiniComponent } from './components/ordini/ordini.component';
import { IndirizziComponent } from './components/indirizzi/indirizzi.component';
import { AggiungiIndirizzoComponent } from './components/aggiungi-indirizzo/aggiungi-indirizzo.component';
import { DettagliAccountComponent } from './components/dettagli-account/dettagli-account.component';

const routes: Routes = [

  { path: '', component: HomepageComponent },
  { path: 'chi-sono', component: AboutComponent },
  { path: 'contatti', component: ContattiComponent },
  { path: 'ebook-e-guide', component: EbookComponent },
  { path: 'webinar', component: WebinarComponent },
  { path: 'articoli', component: ArticoliComponent },
  { path: 'aziende', component: AziendeComponent },
  { path: 'privati', component: PrivatiComponent },
  { path: 'autenticazione', component: LoginSignupComponent },
  { path: 'privacy-policy', component: PrivacyPolicyComponent },
  { path: 'cookie-policy', component: CookiePolicyComponent },
  { path: 'termini-e-condizioni', component: TerminiECondizioniComponent },
  { path: 'recupera-password', component: RecuperaPasswordComponent },

  // AREA RISERVATA 
  { path: 'area-riservata/bacheca', component: BachecaComponent },
  { path: 'area-riservata/gestisci-appuntamento', component: GestisciAppuntamentiComponent },
  { path: 'area-riservata/ordini', component: OrdiniComponent },
  { path: 'area-riservata/indirizzi', component: IndirizziComponent },
  { path: 'area-riservata/indirizzi/aggiungi', component: AggiungiIndirizzoComponent },
  { path: 'area-riservata/dettaglio-account', component: DettagliAccountComponent },


  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  // { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
