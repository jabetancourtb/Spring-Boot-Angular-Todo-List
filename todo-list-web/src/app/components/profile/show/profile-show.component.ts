import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { UserDTO } from 'src/app/models/user/userDto.model';
import { UserService } from 'src/app/services/user/user.service';
import { JwtParseService } from 'src/app/services/util/jwtParse.service';

@Component({
  selector: 'app-profile-show',
  templateUrl: './profile-show.component.html',
  styleUrls: ['./profile-show.component.scss']
})
export class ProfileShowComponent implements OnInit {

  public jwtData : object;

  profileForm = new FormGroup({
    fullname : new FormControl(''),
    email : new FormControl('')
  });

  user = new UserDTO(null, null, null, null, null, null);

  constructor(private userService: UserService
    , private jwtParseService: JwtParseService
    , private toastr: ToastrService) { }

  ngOnInit(): void {
    this.jwtData = this.jwtParseService.parseJWT();
    this.getProfileData();
  }

  public getProfileData() {
    let fullname = this.jwtData['fullname'];
    let email = this.jwtData['sub'];

    this.profileForm.patchValue({
      fullname: fullname,
      email: email
    });

    
  }

  public save() {
    let userId = this.jwtData['id'];
    let fullname = this.profileForm.get('fullname').value;
    let email = this.profileForm.get('email').value;
    let enabled = this.jwtData['enabled'];
    let verificationCode = this.jwtData['verificationCode'];

    this.user = new UserDTO(userId, email, null, fullname, enabled, verificationCode);

    let promise = this.userService.updateUser(this.user);

    promise.then(data => {
      if(data) {
        this.toastr.success('The profile has been updated');
      }
    });

    promise.catch(data => {
      if(data) {
        this.toastr.error('An exception has occurred');
      }
    });
  }

}
