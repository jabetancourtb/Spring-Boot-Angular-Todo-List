import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { UserModel } from 'src/app/models/user/user.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { JwtParseService } from 'src/app/services/util/jwtParse.service';
import { TokenService } from 'src/app/services/util/token.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  user: UserModel;
  public jwtData : object;

  loginForm = new FormGroup({
    email : new FormControl(null, Validators.required),
    password : new FormControl(null, Validators.required)
  });

  constructor(private authService: AuthService
    , private tokenService : TokenService
    , private toastr: ToastrService
    , private router: Router
    , private jwtParseService: JwtParseService) { }

  ngOnInit(): void { 
  }

  public login(): void {

    let promise = this.authService.login(this.loginForm.get('email').value, this.loginForm.get('password').value);

    promise.then(data => {
      this.tokenService.setToken(data.jwt);

      let token = this.tokenService.getToken()

      if(token != null && token != "") {
        this.jwtData = this.jwtParseService.parseJWT();
        this.toastr.success('Welcome ' + this.jwtData['fullname']);
        this.router.navigate(['tasks']);
      }
    });

    promise.catch(error => {
      this.toastr.error(error.error.message);
    })
  }

  public goToSignUp(): void {
    this.router.navigateByUrl('/sign-up');
  }
}
