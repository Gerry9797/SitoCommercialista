import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { HeaderComponent } from './components/header/header.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { FooterComponent } from './components/footer/footer.component';
import { SideMenuComponent } from './components/side-menu/side-menu.component';
import { AboutComponent } from './components/about/about.component';
import { ContattiComponent } from './components/contatti/contatti.component';
import { LottieModule } from 'ngx-lottie';
import player from 'lottie-web';
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
import { NoticesBlockComponent } from './bricks/notices-block/notices-block.component';
import { BachecaComponent } from './components/bacheca/bacheca.component';
import { GestisciAppuntamentiComponent } from './components/gestisci-appuntamenti/gestisci-appuntamenti.component';
import { LoadingSpinnerClessidraComponent } from './components/loaders/loading-spinner-clessidra/loading-spinner-clessidra.component';
import { LoadingSectionComponent } from './bricks/loading-section/loading-section.component';
import { OrdiniComponent } from './components/ordini/ordini.component';
import { IndirizziComponent } from './components/indirizzi/indirizzi.component';
import { AggiungiIndirizzoComponent } from './components/aggiungi-indirizzo/aggiungi-indirizzo.component';
import { SelectAutocompleteFieldComponent } from './bricks/select-autocomplete-field/select-autocomplete-field.component';
// import { AutoCompleteModule } from 'primeng/autocomplete';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { OverlayModule } from '@angular/cdk/overlay';
import { AsyncPipe } from '@angular/common';
import { InputWithValidationComponent } from './bricks/input-with-validation/input-with-validation.component';
import { DettagliAccountComponent } from './components/dettagli-account/dettagli-account.component';

export function playerFactory() {
  return player;
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    HeaderComponent,
    HomepageComponent,
    FooterComponent,
    SideMenuComponent,
    AboutComponent,
    ContattiComponent,
    EbookComponent,
    WebinarComponent,
    ArticoliComponent,
    AziendeComponent,
    PrivatiComponent,
    LoginSignupComponent,
    PrivacyPolicyComponent,
    CookiePolicyComponent,
    TerminiECondizioniComponent,
    RecuperaPasswordComponent,
    NoticesBlockComponent,
    BachecaComponent,
    GestisciAppuntamentiComponent,
    LoadingSpinnerClessidraComponent,
    LoadingSectionComponent,
    OrdiniComponent,
    IndirizziComponent,
    AggiungiIndirizzoComponent,
    SelectAutocompleteFieldComponent,
    InputWithValidationComponent,
    DettagliAccountComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    LottieModule.forRoot({ player: playerFactory }),
    // AutoCompleteModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    OverlayModule,
    AsyncPipe
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
