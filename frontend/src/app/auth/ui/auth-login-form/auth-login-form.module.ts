import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuthLoginFormComponent } from './auth-login-form.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [AuthLoginFormComponent],
  exports: [AuthLoginFormComponent],
})
export class AuthLoginFormModule {}
