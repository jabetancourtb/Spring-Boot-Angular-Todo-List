import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserModel } from 'src/app/models/user/user.model';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-confirm-sign-up',
  templateUrl: './confirm-sign-up.component.html',
  styleUrls: ['./confirm-sign-up.component.scss']
})
export class ConfirmSignUpComponent implements OnInit {

  public user : UserModel = new UserModel(null, null, null, null, null, null);

  public sucessfulVerification: boolean;

  readonly parameters: Object =
  {
    verificationCodeURL : this.activeRoute.snapshot.params['verificationCode']
  }

  constructor(private userService: UserService
    , private activeRoute: ActivatedRoute
    , private router: Router
    , private toastr: ToastrService) { }

  ngOnInit(): void {
    this.getUserByVerificationCode(this.parameters['verificationCodeURL'].toString())
  }

  public getUserByVerificationCode(verificationCode: string) {
    this.userService.getUserByVerificationCode(verificationCode).subscribe(res => {
      this.user = res;
      this.updateState(this.user);
    },
    err => {
      this.toastr.error('The URL is not valid');
      this.router.navigate(['login']);
    });
  }

  public updateState(user: UserModel) {
    if(user != null && user != undefined && this.parameters['verificationCodeURL'].toString() == user.verificationCode) {
      this.userService.updateState(user.id, true).subscribe(res => {
        this.sucessfulVerification = true;
        this.updateVerificationCode(user);
      },
      err => {
        this.sucessfulVerification = false;
        this.toastr.error(err.error);
      });
    }
    else {
      this.sucessfulVerification = false;
    }
  }

  public updateVerificationCode(user: UserModel) {
    this.userService.updateVerificationCode(user.id).subscribe(res => {
      this.toastr.success('user confirmed successfully');
    },
    error => {
      this.toastr.error(error.error);
    });
  }

  public goToLogin() {
    this.router.navigate(['login']);
  }

}
