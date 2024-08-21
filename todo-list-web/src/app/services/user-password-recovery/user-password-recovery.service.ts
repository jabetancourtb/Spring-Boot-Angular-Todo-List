import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { ToastrService } from "ngx-toastr";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { HeadersService } from "../util/header.service";

@Injectable({
    providedIn: 'root'
})

export class UserPasswordRecoveryService {

    constructor(private http : HttpClient
        , private cookieService : CookieService
        , private headersService : HeadersService
        , private toastr: ToastrService) { }


    getUserPasswordRecoveryByVerificationCode(verificationCode: string) : Observable<any> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user-password-recovery/get-user-password-recovery/' + verificationCode;
        return this.http.get<any>(apiUrl, this.headersService.authorizationHeader).pipe(catchError(err => {
            return throwError(err);
        }));
    }
    


    sendRecoveryEmail(email: string) : Observable<any> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user-password-recovery/send-recovery-email/' + email;
        return this.http.post<any>(apiUrl, null, this.headersService.authorizationHeader).pipe(catchError(err => {
            return throwError(err);
        }));
    }

    updatePasswordAndVerificationCode(verificationCode: string, newPassword: string) : Observable<any> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user-password-recovery/update-password/' + verificationCode + "/" + newPassword;
        return this.http.post<any>(apiUrl, null, this.headersService.authorizationHeader).pipe(catchError(err => {
            return throwError(err);
        }));
    }
}
