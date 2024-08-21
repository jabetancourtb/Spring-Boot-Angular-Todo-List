import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class TokenService {

  constructor(private cookieService : CookieService) { }

  getToken() : String {
    //return sessionStorage.getItem('token');
    return this.cookieService.get(environment.AUTHORIZATION_HEADER);
  }

  setToken(token: string) : void {
    //sessionStorage.setItem('token', token);
    this.cookieService.set(environment.AUTHORIZATION_HEADER, token, { expires : 5/24 });
  }

}
