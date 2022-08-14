import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginDTO } from 'src/core/dtos/login.dto';
import { RegisterDTO } from 'src/core/dtos/register.dto';
import { FormData } from 'src/core/interfaces/form/form-data.interface';
import { AuthenticationService } from 'src/core/services/authentication.service';
import { LOGIN_FORM_DATA } from 'src/shared/data/form/login-form.data';
import { REGISTER_FORM_DATA } from 'src/shared/data/form/register-form.data';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.page.html',
  styleUrls: ['./authentication.page.scss'],
})
export class AuthenticationPage {
  loginForm: FormData = LOGIN_FORM_DATA;

  registerForm: FormData = REGISTER_FORM_DATA;

  isAuthenticated$: Observable<boolean> | undefined;

  constructor(
    private authenticationSrv: AuthenticationService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  login(loginDTO: LoginDTO) {
    this.authenticationSrv.login(loginDTO).subscribe({
      next: () => this.router.navigateByUrl('home'),
      error: () => this.openSnackBar('Invalid credentials'),
    });
  }

  register(registerDTO: RegisterDTO) {
    const { password, repeatedPassword } = registerDTO;

    if (password !== repeatedPassword) return this.openSnackBar('Password should coincide');

    this.authenticationSrv.register(registerDTO).subscribe({
      next: () => this.login({ username: registerDTO.username, password: registerDTO.password }),
      error: message => this.openSnackBar(message),
    });
  }

  onFormGroupSubmit(dto: LoginDTO | RegisterDTO, formGroup: string) {
    if (formGroup === 'login') return this.login(dto as LoginDTO);

    if (formGroup === 'register') return this.register(dto as RegisterDTO);

    this.openSnackBar('Something weird has happened');
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', { duration: 5000 });
  }
}
