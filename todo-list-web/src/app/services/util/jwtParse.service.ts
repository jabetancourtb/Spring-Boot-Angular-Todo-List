import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class JwtParseService {

  constructor(private cookieService : CookieService) { }

  parseJWT(): string[]{
    return JSON.parse(atob(this.cookieService.get(environment.AUTHORIZATION_HEADER).split('.')[1]));
  }
  
}
