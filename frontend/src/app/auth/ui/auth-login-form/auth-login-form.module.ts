import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthLoginFormComponent } from './auth-login-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [AuthLoginFormComponent],
  exports: [AuthLoginFormComponent],
})
export class AuthLoginFormModule {}
