import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { AuthService } from '../data-access/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.page.html',
  styleUrls: ['./auth.page.scss'],
})
export class AuthPage implements OnInit {
  loginForm: FormGroup = new FormGroup({});

  registerForm: FormGroup = new FormGroup({});

  isAuthenticated$: Observable<boolean> | undefined;

  constructor(
    private formBldr: FormBuilder,
    private authSrv: AuthService,
    private snackbarService: SnackbarService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBldr.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });

    this.registerForm = this.formBldr.group({
      username: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      password: [null, [Validators.required]],
      repeatedPassword: [null, [Validators.required]],
    });
  }

  login(loginDTO: any) {
    this.authSrv.login(loginDTO).subscribe({
      next: () => this.router.navigateByUrl(''),
      error: () => this.snackbarService.openSnackBar('Invalid credentials'),
    });
  }

  register(registerDTO: any) {
    const { password, repeatedPassword } = registerDTO;

    if (password !== repeatedPassword) return this.snackbarService.openSnackBar('Password should coincide');

    this.authSrv.register(registerDTO).subscribe({
      next: () => this.login({ username: registerDTO.username, password: registerDTO.password }),
      error: message => this.snackbarService.openSnackBar(message),
    });
  }
}
