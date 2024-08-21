import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { UserDTO } from "src/app/models/user/userDto.model";
import { UserModel } from "src/app/models/user/user.model";
import { environment } from "src/environments/environment";
import { CookieService } from "ngx-cookie-service";
import { JwtModel } from "src/app/models/jwt/jwt.model";
import { HeadersService } from "../util/header.service";
import { catchError } from 'rxjs/operators';
import { ToastrService } from "ngx-toastr";


@Injectable({
    providedIn: 'root'
})

export class UserService {

    constructor(private http : HttpClient
        , private cookieService : CookieService
        , private headersService : HeadersService
        , private toastr: ToastrService) { }

        
    signUp(userDto: UserDTO) : Observable<UserModel> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + "/user/sign-up";
        return this.http.post<UserModel>(apiUrl, userDto).pipe(
            catchError(err => {
                this.toastr.success('The email has been sent');
                return throwError(err);
            })
        );
    }

    
    updateUser(userDto: UserDTO) : Promise<JwtModel>{
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user';
        return new Promise((resolve, reject) => {
            this.http.put<JwtModel>(apiUrl, userDto).subscribe(data => {
                    this.cookieService.set(environment.AUTHORIZATION_HEADER, data.jwt, { expires : 5/24 });
                    resolve(data);
                }, 
                (error) => {
                    reject(error);
                }
            );
        });
    }

    updatePassword(userId: number, currentPassword: string, newPassword: string) : Observable<any> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user/update-password/' + userId + "/" + currentPassword + "/" + newPassword;
        return this.http.put<any>(apiUrl, null, this.headersService.authorizationHeader).pipe(catchError(err => {
            return throwError(err);
        }));
    }

    getUserByVerificationCode(verificationCode: string): Observable<UserModel> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user/get-user-by-verification-code/' + verificationCode;
        return this.http.get<any>(apiUrl, this.headersService.authorizationHeader).pipe(catchError(err => {
            return throwError(err);
        }));
    }

    getUserByEmail(email: string): Observable<UserModel> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user/get-user-by-email/' + email;
        return this.http.get<any>(apiUrl, this.headersService.authorizationHeader).pipe(catchError(err => {
            return throwError(err);
        }));
    }

    updateVerificationCode(userId: number) : Observable<any> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user/update-verification-code/' + userId;
        return this.http.put<any>(apiUrl, null, this.headersService.authorizationHeader).pipe(catchError(err => {
            return throwError(err);
        }));
    }

    updateState(userId: number, state: boolean) : Observable<any> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + '/user/update-state/' + userId + "/" + state;
        return this.http.put<any>(apiUrl, null, this.headersService.authorizationHeader).pipe(catchError(err => {
            return throwError(err);
        }));
    }
    
}
