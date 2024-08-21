import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenService } from "src/app/services/util/token.service";
import { environment } from "src/environments/environment";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private tokenService: TokenService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        let token = this.tokenService.getToken();

        if (token != null) {
          const authReq = req.clone({
            headers: req.headers.set(environment.AUTHORIZATION_HEADER, 'Bearer ' + token)
          });
    
          return next.handle(authReq);
        }
    
        return next.handle(req);
    }
    
}