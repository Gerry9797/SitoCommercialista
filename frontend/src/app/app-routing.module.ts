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
import { ConsulenzaAziendaComponent } from './components/consulenza-azienda/consulenza-azienda.component';
import { ConsulenzaPrivatiComponent } from './components/consulenza-privati/consulenza-privati.component';
import { PrenotaConsulenzaComponent } from './prenota-consulenza/prenota-consulenza.component';
import { LoadingPlaceholderComponent } from './bricks/loading-placeholder/loading-placeholder.component';
import { AuthGuardService } from './services/route-guards/auth-guard.service';
import { ErrorComponent } from './components/error/error.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ConfirmRegistrationComponent } from './components/confirm-registration/confirm-registration.component';

const routes: Routes = [

  // PUBLIC PAGES
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
  { path: 'aziende/consulenza', component: ConsulenzaAziendaComponent },
  { path: 'privati/consulenza', component: ConsulenzaPrivatiComponent },
  { path: 'prenota-consulenza', component: PrenotaConsulenzaComponent },

  // AREA RISERVATA 
  { path: 'area-riservata/bacheca', component: BachecaComponent, canActivate: [AuthGuardService]},
  { path: 'area-riservata/gestisci-appuntamento', component: GestisciAppuntamentiComponent, canActivate: [AuthGuardService] },
  { path: 'area-riservata/ordini', component: OrdiniComponent, canActivate: [AuthGuardService] },
  { path: 'area-riservata/indirizzi', component: IndirizziComponent, canActivate: [AuthGuardService] },
  { path: 'area-riservata/indirizzi/aggiungi', component: AggiungiIndirizzoComponent, canActivate: [AuthGuardService] },
  { path: 'area-riservata/dettaglio-account', component: DettagliAccountComponent, canActivate: [AuthGuardService] },
  { path: 'area-riservata/logout', component: LogoutComponent, canActivate: [AuthGuardService] },

  // UTILITY PAGES
  { path: 'account/:id/conferma/:token', component: ConfirmRegistrationComponent },


  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  // { path: '', redirectTo: 'home', pathMatch: 'full' }

  {  path: 'loading-placeholder', component: LoadingPlaceholderComponent },

  { path: 'error', component: ErrorComponent },
  { path: '**', component: ErrorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
