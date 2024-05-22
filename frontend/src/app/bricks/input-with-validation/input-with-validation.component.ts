import { isPlatformBrowser } from '@angular/common';
import { Component, Inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-input-with-validation',
  templateUrl: './input-with-validation.component.html',
  styleUrls: ['./input-with-validation.component.css']
})
export class InputWithValidationComponent implements OnInit {

  @Input() formRef: FormGroup | null = null;
  @Input() controlName: string = "";
  @Input() type: string = "";
  @Input() placeholder: string = "";
  @Input() hideFooterOnFocus = true;
  @Input() maxChars: number | null = null
  @Input() id: string = Math.random().toString();
  @Input() validation: boolean = true;
  @Input() autocompleteName: string = "";

  currentTypePassword: string = "password";
  formControlInput: AbstractControl | null = new FormControl(null);

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
  ) { }

  ngOnInit(): void {
    this.formControlInput = this.formRef!.get(this.controlName);
    
    if (isPlatformBrowser(this.platformId)) {
      if(this.hideFooterOnFocus){
        setTimeout(() => {
          this.handleFooterProblemRisedUpOnMobileKayboardAppears();
        }, 200);
      }
    }
  }

  handleFooterProblemRisedUpOnMobileKayboardAppears(){
    document.querySelectorAll('#'+this.id).forEach(
      (inputElem) => {
        inputElem.addEventListener('focus', (event) => { document.querySelector('#waves-footer')?.classList.add('hide-footer')});
        inputElem.addEventListener('focusout', (event) => { document.querySelector('#waves-footer')?.classList.remove('hide-footer')});
      }
    )
  }

  changeTypePasswordInText(){
    this.currentTypePassword = 'text';
  }

  changeTypePasswordInPassword(){
    this.currentTypePassword = 'password';
  }

}
