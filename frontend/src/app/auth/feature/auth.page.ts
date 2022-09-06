import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { AuthStore } from '../data-access/auth.store';
import { Credentials } from '../data-access/dto/credentials.dto';
import { RegisterUserData } from '../data-access/dto/register-user.dto';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.page.html',
  styleUrls: ['./auth.page.scss'],
})
export class AuthPage implements OnInit {
  registerForm: FormGroup = new FormGroup({});

  isAuthenticated$: Observable<boolean> | undefined;

  constructor(private authStore: AuthStore, private snackbarService: SnackbarService, private router: Router) {}

  ngOnInit(): void {}

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
