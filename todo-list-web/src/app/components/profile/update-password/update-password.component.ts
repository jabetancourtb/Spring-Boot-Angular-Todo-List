import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user/user.service';
import { JwtParseService } from 'src/app/services/util/jwtParse.service';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.scss']
})
export class UpdatePasswordComponent implements OnInit {

  public jwtData : object;

  passwordForm = new FormGroup({
    currentPassword : new FormControl(''),
    newPassword : new FormControl(''),
    verifyNewPassword : new FormControl('')
  });

  constructor(private userService: UserService
    , private jwtParseService: JwtParseService
    , private toastr: ToastrService) { }

  ngOnInit(): void {
    this.jwtData = this.jwtParseService.parseJWT();
  }

  public verifyPasswords() {
    let userId = this.jwtData['id']
    let currentPassword = this.passwordForm.get('currentPassword').value;
    let newPassword = this.passwordForm.get('newPassword').value;
    let verifyNewPassword = this.passwordForm.get('verifyNewPassword').value;

    if(newPassword === verifyNewPassword){
      this.save(userId, currentPassword, newPassword);
    }
    else {
      this.toastr.error('Passwords do not match');
    }
  }

  public save(userId: number, currentPassword: string, newPassword: string) {
    this.userService.updatePassword(userId, currentPassword, newPassword).subscribe(res => {
      this.toastr.success('The password has been updated');
    },
    err => {
      this.toastr.error(err.error);
    });
  }

}
