import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { SITE_CONFIG } from 'src/app/app.config';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-dettagli-account',
  templateUrl: './dettagli-account.component.html',
  styleUrl: './dettagli-account.component.css'
})
export class DettagliAccountComponent implements OnInit{

  siteConfig = SITE_CONFIG
  MAX_EMAIL_LEN = this.siteConfig.settings.loginSignup.MAX_EMAIL_LEN
  MAX_PASSWORD_LEN = this.siteConfig.settings.loginSignup.MAX_PASSWORD_LEN
  formSignup!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private utilityService: UtilityService){
    
  }

  ngOnInit(): void {

    this.utilityService.scrollToTop();

    this.formSignup = this.formBuilder.group({
      // signup_email: new FormControl(null, [Validators.required, Validators.email, Validators.maxLength(this.MAX_EMAIL_LEN)]),
      signup_password: new FormControl(null, [Validators.required, Validators.maxLength(this.MAX_PASSWORD_LEN), Validators.pattern(
        /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/
      ),]),
      signup_retypePassword: new FormControl(null, [Validators.required])
    },
    {
      validator: this.utilityService.ConfirmedValidator('signup_password', 'signup_retypePassword'),
    });
  }

  

}
