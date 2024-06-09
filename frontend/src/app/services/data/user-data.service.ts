import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserModel } from 'src/app/models/user.model';
import { UtilityService } from '../utility/utility.service';
import { SITE_CONFIG } from 'src/app/app.config';
import { ChangePasswordRequest } from 'src/app/models/change-password-request.model';

const USER_DATA_API = SITE_CONFIG.settings.backend.baseUrl + 'user/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  constructor(
    private http: HttpClient,
    private utilityService: UtilityService) { }

  getAccountDetails(userId: number): Observable<ModelAccount.AccountBean> {
    return this.http.get<ModelAccount.AccountBean>(USER_DATA_API + `account/${userId}/details`, httpOptions);
  }

  confirmRegistration(userId: number, verificationCode: string) : Observable<UserModel>{
    return this.http.put<UserModel>(USER_DATA_API + `account/${userId}/confirm/${verificationCode}`, null, httpOptions);
  }

  sendEmailToConfirmEmailChange(userId: number, newEmail: string) {
    return this.http.put<Boolean>(USER_DATA_API + `account/${userId}/changeEmailRequest`, newEmail, httpOptions);
  }

  confirmChangeEmail(userId: number, verificationCode: string, newEmail: string) : Observable<UserModel>{
    return this.http.put<UserModel>(USER_DATA_API + `account/${userId}/newEmail/${newEmail}/${verificationCode}`, null);
  }

  changePassword(userId: number, request: ChangePasswordRequest) {
    return this.http.put<UserModel>(USER_DATA_API + `account/${userId}/newPassword`, request);
  }

  sendMailToRecoverPassword(email: string) {
    return this.http.put<Boolean>(USER_DATA_API + `account/${email}/sendMailToRecoverPassword`, null);
  }

  resetPassword(userId: number, verificationCode: string, request: ChangePasswordRequest) {
    request.verificationCode = verificationCode;
    return this.http.put<boolean>(USER_DATA_API + `account/${userId}/resetPassword`, request, httpOptions);
  }

}
