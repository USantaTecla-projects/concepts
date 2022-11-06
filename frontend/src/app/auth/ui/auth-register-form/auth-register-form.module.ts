import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuthRegisterFormComponent } from './auth-register-form.component';

@NgModule({
  imports: [CommonModule, MaterialModule, SharedModule],
  declarations: [AuthRegisterFormComponent],
  exports: [AuthRegisterFormComponent],
})
export class AuthRegisterFormModule {}
