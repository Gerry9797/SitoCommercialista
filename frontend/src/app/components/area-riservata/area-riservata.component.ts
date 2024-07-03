import { isPlatformBrowser } from '@angular/common';
import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { NotificationMessage } from 'src/app/models/notification-message.model';
import { UserModel } from 'src/app/models/user.model';
import { UserDataService } from 'src/app/services/data/user-data.service';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-area-riservata',
  templateUrl: './area-riservata.component.html',
  styleUrl: './area-riservata.component.css'
})
export class AreaRiservataComponent {

  activeItem: string = "";
  showBorderBlock: boolean = false;

  user: UserModel = {} as UserModel;
  accountDet: ModelAccount.AccountBean = {} as ModelAccount.AccountBean;

  message: NotificationMessage | null = null;

  // message: NotificationMessage = {
  //   description: 'Indirizzo modificato correttamente.',
  //   status: 'message',
  // };

  constructor(
    private utilityService: UtilityService,
    @Inject(PLATFORM_ID) private platformId: Object,
    private tokenStorage: TokenStorageService,
    private userDataService: UserDataService,
    private route: ActivatedRoute 
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.getActiveItem();
      this.utilityService.scrollToTop();
      this.getDataLoggedUser();
      if (this.user.id) {
        this.getAccountDetails();
      }
    }
  }

  getActiveItem() {
    this.route.data.subscribe(data => {
      this.activeItem = data['activeItem'];
      this.showBorderBlock = data['showBorderBlock'];
    });
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
