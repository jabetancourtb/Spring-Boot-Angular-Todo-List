import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserPasswordRecoveryModel } from 'src/app/models/user-password-recovery/user-password-recovery.model';
import { UserModel } from 'src/app/models/user/user.model';
import { UserPasswordRecoveryService } from 'src/app/services/user-password-recovery/user-password-recovery.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-recovery-password',
  templateUrl: './recovery-password.component.html',
  styleUrls: ['./recovery-password.component.scss']
})
export class RecoveryPasswordComponent implements OnInit {

  public userPasswordRecoveryModel = new UserPasswordRecoveryModel(null, null, null, null, null);

  public user : UserModel = new UserModel(null, null, null, null, null, null);

  readonly parameters: Object =
  {
    verificationCodeURL : this.activeRoute.snapshot.params['verificationCode']
  }

  recoveryPasswordForm = new FormGroup({
    email : new FormControl(null, Validators.required),
  });

  passwordForm = new FormGroup({
    newPassword : new FormControl('')
  });
  

  constructor(private userService: UserService
    , private userPasswordRecoveryService: UserPasswordRecoveryService
    , private toastr: ToastrService
    , private activeRoute: ActivatedRoute
    , private router: Router) { }


  ngOnInit(): void {

    let verificationCode = null;

    if(this.parameters['verificationCodeURL']) {
      verificationCode = this.parameters['verificationCodeURL'].toString();
      this.getUserPasswordRecoveryByVerificationCode(verificationCode);
    }
    
  }


  public getUserByEmail(email: string) {
    this.userService.getUserByEmail(email).subscribe(res => {
      this.user = res;
      this.sendRecoveryEmail(email);
    },
    err => {
      this.toastr.error(err.error.message);
    });
  }


  public sendRecoveryEmail(email : string) {
    this.toastr.success('The email has been sent, it may take a few minutes to send, please check your inbox if not check your spam.');
    this.userPasswordRecoveryService.sendRecoveryEmail(email).subscribe(res => {
    },
    err => {
      this.toastr.error(err.error.message);
    });
  }


  public sendEmail() {
    let email = this.recoveryPasswordForm.get('email').value;
    this.getUserByEmail(email);
  }


  public getUserPasswordRecoveryByVerificationCode(verificationCode: string) {
    this.userPasswordRecoveryService.getUserPasswordRecoveryByVerificationCode(verificationCode).subscribe(res => {
      this.userPasswordRecoveryModel = res;
    },
    err => {
      this.toastr.error(err.error.message);
      this.goToLogIn();
    });
  }


  public updatePasswordAndVerificationCode() {

    let verificationCode = this.parameters['verificationCodeURL'];

    let newPassword = this.passwordForm.get('newPassword').value;

    this.userPasswordRecoveryService.updatePasswordAndVerificationCode(verificationCode, newPassword).subscribe(res => {
      this.toastr.success("Password has been reset");

      setTimeout(() => {
        this.goToLogIn();
      }, 500);
    },
    err => {
      this.toastr.error(err.error.message);
      this.goToLogIn();
    });
  }
  

  public goToLogIn(): void {
    this.router.navigateByUrl('/login');
  }

}
