import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";
import { AuthService } from 'src/app/_services/auth.service';
import { SITE_CONFIG } from 'src/app/app.config';
import { UserModel } from 'src/app/models/user.model';
import { UserDataService } from 'src/app/services/data/user-data.service';


@Component({
  selector: 'app-confirm-registration',
  templateUrl: './confirm-registration.component.html',
  styleUrls: ['./confirm-registration.component.css']
})
export class ConfirmRegistrationComponent implements OnInit {

  id!: number;
  verificationCode!: string
  verifiedUser: UserModel | null = null;
  isLogged: boolean = false;

  isError: boolean = false;

  supportEmail = SITE_CONFIG.support.email;

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private userService: UserDataService,
    private spinner: NgxSpinnerService,
    private router: Router
    ) { }

  ngOnInit(): void {
    // show the loading spinner
    this.spinner.show("spinner");

    this.id = this.route.snapshot.params['id'];
    this.verificationCode = this.route.snapshot.params['token'];

    this.confirmRegistration();
    
  }

  confirmRegistration(){    
    this.userService.confirmRegistration(this.id, this.verificationCode).subscribe(
      response => {
        // console.log(response);
        this.verifiedUser = response;
        this.spinner.hide("spinner");
        // this.automatedLogin();
      },
      error => {
        console.error(error);
        // this.notificationService.showError("E' stato riscontrato un errore, ricarica la pagina per riprovare");
        this.isError = error;
        this.spinner.hide("spinner");
      }
    )
  }

  // automatedLogin(){
  //   this.authService.login(this.verifiedUser!.email, this.verifiedUser!.password).subscribe(
  //     data => {
  //       this.tokenStorage.saveToken(data.accessToken);
  //       this.tokenStorage.saveUser(data);
  //       this.spinner.hide("spinner");
  //       this.isLogged = true;
  //       this.redirectAfterLogin();
  //   },
  //   err => {

  //     this.spinner.hide("spinner");
  //     this.isLogged = false; 
  //   }
  // );
  // }

}
