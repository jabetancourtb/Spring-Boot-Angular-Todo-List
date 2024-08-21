import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from "./components/login/login.component";
import { ProfileShowComponent } from "./components/profile/show/profile-show.component";
import { UpdatePasswordComponent } from "./components/profile/update-password/update-password.component";
import { RecoveryPasswordComponent } from "./components/recovery-password/recovery-password.component";
import { ConfirmSignUpComponent } from "./components/sign-up/confirm/confirm-sign-up.component";
import { SignUpComponent } from "./components/sign-up/sign-up/sign-up.component";
import { TaskListComponent } from "./components/task/list/task-list.component";
import { AuthGuard } from "./guard/auth.guard";

const APP_ROUTES: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'sign-up', component: SignUpComponent },
    { path: 'confirm-sign-up/:verificationCode', component: ConfirmSignUpComponent },
    { path: 'profile', component: ProfileShowComponent, canActivate: [AuthGuard] },
    { path: 'update-password', component: UpdatePasswordComponent, canActivate: [AuthGuard] },
    { path: 'recovery-password', component: RecoveryPasswordComponent },
    { path: 'recovery-password/:verificationCode', component: RecoveryPasswordComponent },
    { path: 'tasks', component: TaskListComponent, canActivate: [AuthGuard] },
    { path: '', component: TaskListComponent, canActivate: [AuthGuard] },
    { path: '**', pathMatch: 'full', redirectTo: 'login' }
];

export const APP_ROUTING = RouterModule.forRoot(APP_ROUTES, { useHash: true });