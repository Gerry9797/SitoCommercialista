export interface ChangePasswordRequest {
    oldPassword?: string;
    newPassword: string;
    verificationCode?: string;
}