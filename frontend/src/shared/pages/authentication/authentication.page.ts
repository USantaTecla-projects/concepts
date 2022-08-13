import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { catchError, first, tap } from 'rxjs';
import { LoginDTO } from 'src/core/dtos/login.dto';
import { FormData } from 'src/core/interfaces/form-data.interface';
import { FormValues } from 'src/core/interfaces/form-values.interface';
import { AuthenticationService } from 'src/core/services/authentication.service';
import { mapFormValuesToData } from 'src/core/utils/form-data-mapper.util';
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

  constructor(
    private authenticationSrv: AuthenticationService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  login(loginDTO: LoginDTO) {
    this.authenticationSrv
      .login(loginDTO)
      .pipe(
        first(),
        catchError(error => {
          this.openSnackBar('Incorrect credentials');
          return error;
        })
      )
      .subscribe(() => this.router.navigateByUrl('home'));
  }

  onFormGroupSubmit(formValues: FormValues, formGroup: string) {
    if (formGroup === 'login') {
      const loginDTO: LoginDTO = mapFormValuesToData<LoginDTO>(formValues);
      return this.login(loginDTO);
    }

    if (formGroup === 'register') {
      return;
    }

    this.openSnackBar('Something weird has happened');
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', { duration: 5000 });
  }
}
