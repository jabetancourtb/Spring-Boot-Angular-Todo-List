import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class HeadersService {
  authorizationHeader = {
    headers: new HttpHeaders({
      'Authorization' : 'Bearer ' + this.cookieService.get(environment.AUTHORIZATION_HEADER)
  })}

  authorizationHeaderAndJsonContentType = {
    headers: new HttpHeaders({
      'Authorization' : this.cookieService.get(environment.AUTHORIZATION_HEADER),
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    })}
    
  constructor(private cookieService : CookieService) { }
}
