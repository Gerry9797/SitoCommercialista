import { LOCALE_ID, NgModule } from '@angular/core';
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
import { AsyncPipe, registerLocaleData } from '@angular/common';
import { InputWithValidationComponent } from './bricks/input-with-validation/input-with-validation.component';
import { DettagliAccountComponent } from './components/dettagli-account/dettagli-account.component';
import { ConsulenzaAziendaComponent } from './components/consulenza-azienda/consulenza-azienda.component';
import { ConsulenzaPrivatiComponent } from './components/consulenza-privati/consulenza-privati.component';
import { PrenotaConsulenzaComponent } from './prenota-consulenza/prenota-consulenza.component';
import { LoadingPlaceholderComponent } from './bricks/loading-placeholder/loading-placeholder.component';
import { PresentationComponent } from './components/presentation/presentation.component';
import { BusinessButtonsComponent } from './bricks/business-buttons/business-buttons.component';
import { ServiceCardComponent } from './bricks/cards/service-card/service-card.component';
import { ServicesPresentationComponent } from './components/services-presentation/services-presentation.component';
import { MacrocardImpressionComponent } from './bricks/macrocard-impression/macrocard-impression.component';
import { AppearDirective } from './directives/appear.directive';
import { ArticleCardComponent } from './bricks/cards/article-card/article-card.component';
import { RecentArticlesComponent } from './components/recent-articles/recent-articles.component';
import { SideCartComponent } from './components/side-cart/side-cart.component';
import { SearchFormFullScreenComponent } from './components/search-form-full-screen/search-form-full-screen.component';
import localeIt from '@angular/common/locales/it';
import { IntestazioneAreaRiservataComponent } from './bricks/intestazione-area-riservata/intestazione-area-riservata.component';
import { InnerMenuAreaRiservataComponent } from './bricks/inner-menu-area-riservata/inner-menu-area-riservata.component';
import { MainTitleCenterComponent } from './bricks/main-title-center/main-title-center.component';
import { MainTitleDecorationComponent } from './bricks/main-title-decoration/main-title-decoration.component';
import { ConsulenzaCardComponent } from './bricks/cards/consulenza-card/consulenza-card.component';
import { PhoneNumberFormatterPipe } from './pipes/phone-number-formatter.pipe';

export function playerFactory() {
  return player;
}

// Registra i dati della lingua italiana
registerLocaleData(localeIt);

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
    DettagliAccountComponent,
    ConsulenzaAziendaComponent,
    ConsulenzaPrivatiComponent,
    PrenotaConsulenzaComponent,
    LoadingPlaceholderComponent,
    PresentationComponent,
    BusinessButtonsComponent,
    ServiceCardComponent,
    ServicesPresentationComponent,
    MacrocardImpressionComponent,
    AppearDirective,
    ArticleCardComponent,
    RecentArticlesComponent,
    SideCartComponent,
    SearchFormFullScreenComponent,
    IntestazioneAreaRiservataComponent,
    InnerMenuAreaRiservataComponent,
    MainTitleCenterComponent,
    MainTitleDecorationComponent,
    ConsulenzaCardComponent,
    PhoneNumberFormatterPipe
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
  providers: [
    authInterceptorProviders,
    // Imposta la lingua italiana come lingua predefinita
    { provide: LOCALE_ID, useValue: 'it-IT' },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
