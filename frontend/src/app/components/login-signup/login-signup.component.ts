import { isPlatformBrowser } from '@angular/common';
import { Component, ElementRef, HostListener, Inject, OnInit, PLATFORM_ID, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/_services/auth.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { SITE_CONFIG } from 'src/app/app.config';
import { NotificationMessage } from 'src/app/models/notification-message.model';
import { ErrorViewerHandlerService } from 'src/app/services/errors/error-viewer-handler.service';
import { InternalSessionManagerService } from 'src/app/services/session/internal-session-manager.service';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-login-signup',
  templateUrl: './login-signup.component.html',
  styleUrls: ['./login-signup.component.css']
})
export class LoginSignupComponent implements OnInit {

  message: NotificationMessage | null = null;

  // message: NotificationMessage = {
  //   description: "Indirizzo email sconosciuto. Ricontrolla o prova ad usare il tuo nome utente.",
  //   status: "error"
  // }

  siteConfig = SITE_CONFIG  
  MAX_EMAIL_LEN = this.siteConfig.settings.loginSignup.MAX_EMAIL_LEN
  MAX_PASSWORD_LEN = this.siteConfig.settings.loginSignup.MAX_PASSWORD_LEN

  technicalError = SITE_CONFIG.errors.msgTechnicalError

  formSignin!: FormGroup;
  formSignup!: FormGroup;


  generalErrorMessageSignin: string | null = null
  generalErrorMessageSignup: string | null = null

  @ViewChild('formAccedi', { static: false }) formAccedi!: ElementRef;
  @ViewChild('formRegistrati', { static: false }) formRegistrati!: ElementRef;
  
  idGeneralNoticesBlock: string = "login-signup-notices-block-id";

  isMobile!: boolean;

  showErrorSignin: boolean = false;
  showErrorSignup: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private utilityService: UtilityService,
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private errorViewer: ErrorViewerHandlerService,
    // private notifyService: NotificationService,
    // private spinner: NgxSpinnerService,
    // private dialogService: DialogService,
    private internalSessionManager: InternalSessionManagerService,
    // private dynamicMetadataService: DynamicMetadataService,
    @Inject(PLATFORM_ID) private platformId: Object,
  ) { 
    this.formSignin = this.formBuilder.group({
      signin_email: new FormControl(null, [Validators.required, Validators.email, Validators.maxLength(this.MAX_EMAIL_LEN)]),
      signin_password: new FormControl(null, [Validators.required, Validators.maxLength(this.MAX_PASSWORD_LEN),] )
    });
  
    this.formSignup = this.formBuilder.group({
      signup_email: new FormControl(null, [Validators.required, Validators.email, Validators.maxLength(this.MAX_EMAIL_LEN)]),
      signup_password: new FormControl(null, [Validators.required, Validators.maxLength(this.MAX_PASSWORD_LEN), Validators.pattern(
        /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/
      ),]),
      signup_retypePassword: new FormControl(null, [Validators.required])
    },
    {
      validator: this.utilityService.ConfirmedValidator('signup_password', 'signup_retypePassword'),
    });
  }

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {

      this.isMobile = this.utilityService.checkIfMobile();

      this.utilityService.scrollToTopInstant();

      if (this.tokenStorage.getToken()) { // se il token è presente
        this.redirectAfterLogin();
      }
    }
  }

      //Questa funzione scatta sull'evento "finestra ridimensionata":
      @HostListener('window:resize', ['$event'])  //se la larghezza della finestra viene modificata si ricontrolla il valore di "mobile" per vedere quale view riportare, se desktop o mobile
      onResize(){
        this.isMobile = this.utilityService.checkIfMobile();
      }

  redirectAfterLogin() {
    this.internalSessionManager.setRefreshAfterLoginLogout();
    this.router.navigate(['/area-riservata/bacheca']);
  }

  signin(){
    //  console.log("signin click");
    //  console.log(this.formSignin);
     let email = this.formSignin.get('signin_email')?.value
     let password = this.formSignin.get('signin_password')?.value

     this.message = null; // resetta il messaggio di errore generico
    
     if(this.checkAndValidateForm(this.formSignin)){
      //  this.spinner.show("spinner");
       this.authService.login(email, password).subscribe(
         data => {
           this.tokenStorage.saveToken(data.accessToken);
           this.tokenStorage.saveUser(data);
          //  this.spinner.hide("spinner");
           this.generalErrorMessageSignin = null
           this.redirectAfterLogin();
         },
         err => {
          debugger
          //  this.spinner.hide("spinner");
           if(err?.error.message){
             this.generalErrorMessageSignin = err.error.message
             this.setAndFocusGeneralNotification(
              {
                title: "Errore",
                description: err.error.message,
                status: "error"
               } as NotificationMessage,
               this.idGeneralNoticesBlock
             );
           }
           else {
            this.setAndFocusGeneralNotification(
              {
                title: this.technicalError.title,
                description: this.technicalError.message,
                status: "error"
               } as NotificationMessage,
               this.idGeneralNoticesBlock
             );
            //  this.notifyService.showError(this.translateService.instant('general.errors.msgTechnicalError'));
           }
           
         }
       );
     }
     else {
      //  console.log("form invalid");
      this.showErrorSignin = true;
     }
 
     
   }

   signup(){
    //  console.log("signup click");
    //  console.log(this.formSignup);
    debugger;
 
     let username = ""; // non gestito uno username
     let email = this.formSignup.get('signup_email')?.value
     let password = this.formSignup.get('signup_password')?.value
 
     if(this.checkAndValidateForm(this.formSignup)){
      //  this.spinner.show("spinner");
       this.authService.register(username, email, password).subscribe(
         data => {
          //  this.spinner.hide("spinner");
          //  this.openModalEmailSent(email);
           this.generalErrorMessageSignup = null;
         },
         err => {
           if(err?.error.message){
             this.generalErrorMessageSignup = err.error.message
             this.setAndFocusGeneralNotification(
              {
                title: "Errore",
                description: err.error.message,
                status: "error"
               } as NotificationMessage,
               this.idGeneralNoticesBlock
             );
           }
           else {
            this.setAndFocusGeneralNotification(
              {
                title: this.technicalError.title,
                description: this.technicalError.message,
                status: "error"
               } as NotificationMessage,
               this.idGeneralNoticesBlock
             );
            //  this.notifyService.showError(this.translateService.instant('general.errors.msgTechnicalError'));
           }
          //  this.spinner.hide("spinner");
         }
       );
     }
     else {
      //  console.log("form invalid");
      this.showErrorSignup = true;
     }
   }

   checkAndValidateForm(formToCheck: FormGroup){
    Object.keys(formToCheck.controls).forEach(field => { // {1}
      const control = formToCheck.get(field);            // {2}
      control?.markAsTouched({ onlySelf: true });       // {3}
    });
    return formToCheck.valid
  }

  scrollToLoginFormOnMobile() {
    if(this.isMobile) {
      let elementPosition = this.formAccedi.nativeElement.getBoundingClientRect().top;
      let offsetPosition = elementPosition + window.pageYOffset - 200;

      window.scrollTo({
        top: offsetPosition,
        behavior: 'smooth'
      });
    }
  }

  scrollToRegistrationFormOnMobile () {
    if(this.isMobile) {
      let elementPosition = this.formRegistrati.nativeElement.getBoundingClientRect().top;
      let offsetPosition = elementPosition + window.pageYOffset - 200;

      window.scrollTo({
        top: offsetPosition,
        behavior: 'smooth'
      });
    }
  }

  setAndFocusGeneralNotification(notification: NotificationMessage, idGeneralNoticesBlock: string) {
    this.message = notification;
    setTimeout(() => {
      this.utilityService.scrollToElementIfNotInViewExcludingHeader(this.idGeneralNoticesBlock);
    }, 500);
  }

}
