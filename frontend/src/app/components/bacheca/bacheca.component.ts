import { isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { UserModel } from 'src/app/models/user.model';
import { UserDataService } from 'src/app/services/data/user-data.service';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-bacheca',
  templateUrl: './bacheca.component.html',
  styleUrls: ['./bacheca.component.css'],
})
export class BachecaComponent implements OnInit {
  
  user: UserModel = {} as UserModel;
  accountDet: ModelAccount.AccountBean = {} as ModelAccount.AccountBean;

  constructor(
    private utilityService: UtilityService,
    @Inject(PLATFORM_ID) private platformId: Object,
    private tokenStorage: TokenStorageService,
    private userDataService: UserDataService
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.utilityService.scrollToTop();
      this.getDataLoggedUser();
      if (this.user.id) {
        this.getAccountDetails();
      }
    }
  }

  getDataLoggedUser() {
    this.user = this.tokenStorage.getUser();
  }

  getAccountDetails() {
    this.userDataService.getAccountDetails(this.user.id).subscribe(
      (data) => {
        this.accountDet = data;
      },
      (error) => {
        // if (error?.error?.message) {
        //   this.notifyService.showError(error.error.message);
        // } else {
        //   this.notifyService.showError(
        //     this.translateService.instant('general.errors.msgTechnicalError')
        //   );
        // }
      }
    );
  }
}
