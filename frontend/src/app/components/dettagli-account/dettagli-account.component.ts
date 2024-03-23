import { Component, OnInit } from '@angular/core';
import { MAX_PASSWORD_LEN } from 'src/app/app.constants';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-dettagli-account',
  templateUrl: './dettagli-account.component.html',
  styleUrl: './dettagli-account.component.css'
})
export class DettagliAccountComponent implements OnInit{


  MAX_PASSWORD_LEN = MAX_PASSWORD_LEN
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
