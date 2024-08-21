import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { environment } from "src/environments/environment";
import { CookieService } from "ngx-cookie-service";
import { JwtModel } from "src/app/models/jwt/jwt.model";
import { TokenService } from "../util/token.service";


@Injectable({
    providedIn: 'root'
})

export class AuthService {

    authenticated = false;
    public jwtData : object;

    constructor(private http : HttpClient
        , private cookieService : CookieService
        , private tokenService: TokenService) { }

    isAuthenticated(): boolean {

        let token = this.tokenService.getToken()
        
        if(token != null && token != "") {
            this.authenticated = true;
        }
        else {
            this.authenticated = false;
        }

        return this.authenticated;
    }

    login(email: string, password: string) : Promise<JwtModel> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + "/user/" + email + "/" + password;
        return new Promise((resolve, reject) => {
            this.http.get<JwtModel>(apiUrl).subscribe(data => {
                    this.authenticated = true;
                    resolve(data);
                }, 
                (error) => {
                    reject(error);
                }
            );
        });
    }

    logout(): void {
        this.cookieService.deleteAll();
        this.authenticated = false;
    }
    
}
