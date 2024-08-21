import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(public authService: AuthService
    , private router: Router) { }

  ngOnInit(): void {

  }

  public logout(): void {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}
