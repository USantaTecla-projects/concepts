import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { AuthStore } from '../data-access/auth.store';
import { Credentials } from '../types/dto/credentials.dto';
import { RegisterUserData } from '../types/dto/register-user.dto';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.page.html',
  styleUrls: ['./auth.page.scss'],
})
export class AuthPage {
  registerForm: FormGroup = new FormGroup({});

  isAuthenticated$: Observable<boolean> | undefined;

  constructor(private authStore: AuthStore, private snackbarService: SnackbarService, private router: Router) {}

  onLoginUser(credentials: Credentials): void {
    this.authStore.login(credentials).subscribe({
      next: () => this.router.navigateByUrl(''),
      error: () => this.snackbarService.openSnackBar('Invalid credentials'),
    });
  }

  onRegisterUser(registerUserData: RegisterUserData): void {
    this.authStore.register(registerUserData).subscribe({
      next: () => this.onLoginUser({ ...registerUserData }),
      error: message => this.snackbarService.openSnackBar(message),
    });
  }
}
