import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDTO } from "src/app/models/user/userDto.model";
import { UserModel } from "src/app/models/user/user.model";
import { environment } from "src/environments/environment";
import { CookieService } from "ngx-cookie-service";
import { JwtModel } from "src/app/models/jwt/jwt.model";


@Injectable({
    providedIn: 'root'
})

export class UserService {

    constructor(private http : HttpClient, private cookieService : CookieService) { }

    login(email: string, password: string) : Promise<JwtModel> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + "/user/" + email + "/" + password;
        return new Promise((resolve, reject) => {
            this.http.get<JwtModel>(apiUrl).subscribe(data => {
                    this.cookieService.set(environment.AUTHORIZATION_HEADER, data.jwt, { expires : 5/24 });
                    resolve(data);
                }, 
                (error) => {
                    reject(error);
                }
            );
        });
    }

    signUp(userDto: UserDTO) : Observable<UserModel> {
        let apiUrl : string = environment.API_URL_DEVELOPMENT + "/user";
        return this.http.post<UserModel>(apiUrl, userDto);
    }

    logout(): void {
        this.cookieService.deleteAll();
    }
}
