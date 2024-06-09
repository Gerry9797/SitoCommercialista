declare module ModelAccount{

    export interface AccountBean {
        idUser: number;
        verificationCode?: string;
        status: string;
        dataPubb: Date;
    }
}