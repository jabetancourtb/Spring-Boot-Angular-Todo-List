import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserModel } from 'src/app/models/user/user.model';
import { UserService } from 'src/app/services/user/user.service';
import { JwtParseService } from 'src/app/services/util/jwtParse.service';

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

  constructor(private userService: UserService
    , private toastr: ToastrService
    , private route: ActivatedRoute
    , private router: Router
    , private jwtParseService: JwtParseService) { }

  ngOnInit(): void { 
  }

  public login(): void {

    let promise = this.userService.login(this.loginForm.get('email').value, this.loginForm.get('password').value);

    promise.then(data => {
      this.jwtData = this.jwtParseService.parseJWT();
      this.toastr.success('Welcome ' + this.jwtData['fullname']);
      this.router.navigate(['tasks']);
    });

    promise.catch(error => {
      this.toastr.error('The credentials are wrong');
    })
  }

  public goToSignUp(): void {
      this.router.navigateByUrl('/sign-up');
  }
}
