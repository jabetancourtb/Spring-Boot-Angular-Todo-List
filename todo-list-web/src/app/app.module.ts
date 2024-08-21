import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { MatIconModule } from '@angular/material/icon'

//Routes
import { APP_ROUTING } from "./app.routes";

//Components
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up/sign-up.component';
import { TaskListComponent } from './components/task/list/task-list.component';
import { CookieService } from 'ngx-cookie-service';
import { TaskCreateComponent } from './components/task/create/task-create.component';
import { ModalModule } from 'ngb-modal';
import { TaskUpdateComponent } from './components/task/update/task-update.component';
import { ModalConfirmationComponent } from './components/util/modal-confirmation/modal-confirmation.component';
import { TaskFilterComponent } from './components/task/filter/task-filter.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProfileShowComponent } from './components/profile/show/profile-show.component';
import { ProfileUpdateComponent } from './components/profile/update/profile-update.component';
import { UpdatePasswordComponent } from './components/profile/update-password/update-password.component';
import { ConfirmSignUpComponent } from './components/sign-up/confirm/confirm-sign-up.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { RecoveryPasswordComponent } from './components/recovery-password/recovery-password.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    TaskListComponent,
    TaskCreateComponent,
    TaskUpdateComponent,
    ModalConfirmationComponent,
    TaskFilterComponent,
    NavbarComponent,
    ProfileShowComponent,
    ProfileUpdateComponent,
    UpdatePasswordComponent,
    ConfirmSignUpComponent,
    RecoveryPasswordComponent
  ],
  imports: [
    BrowserModule,
    APP_ROUTING,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule, 
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgbModule,
    NgxPaginationModule,
    ModalModule,
    MatIconModule
  ],
  providers: [CookieService
    , { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
    , { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
