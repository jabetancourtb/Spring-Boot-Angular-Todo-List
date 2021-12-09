import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserModel } from 'src/app/models/user/user.model';
import { UserDTO } from 'src/app/models/user/userDto.model';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

  user: UserModel;

  userForm = new FormGroup({
    email : new FormControl(null, Validators.required),
    password : new FormControl(null, Validators.required),
    fullName : new FormControl(null, Validators.required),
    repeatPassword : new FormControl(null, Validators.required),
  });

  constructor(private userService: UserService
    , private toastr: ToastrService
    , private route: ActivatedRoute
    , private router: Router) { }

  ngOnInit(): void {
    
  }

  public checkPassword()  {
    if (this.userForm.get('password').value == this.userForm.get('repeatPassword').value) {
      this.signUp();
    } else {
      this.toastr.error('The password does not match');
    }
  }

  public signUp() {
    const userDto = new UserDTO(this.userForm.get('email').value, this.userForm.get('password').value, this.userForm.get('fullName').value)
    this.userService.signUp(userDto)
    .subscribe(res => {
      this.user = res;
      this.toastr.success('The user has been registered');

      setTimeout(() => {
        this.router.navigate(['/login']);
      }, 2000);
    });
  }

  public goToLogin(): void {
    this.router.navigateByUrl('/login');
}
}
