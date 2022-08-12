import { CommonModule } from '@angular/common'
import { NgModule } from '@angular/core'

import { ComponentsModule } from 'src/components/components.module'

import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatTabsModule } from '@angular/material/tabs'
import { AuthenticationPage } from './authentication/authentication.page'

@NgModule({
  declarations: [AuthenticationPage],
  imports: [
    CommonModule,
    ComponentsModule,
    MatCardModule,
    MatButtonModule,
    MatTabsModule,
  ],
  exports: [AuthenticationPage],
})
export class PagesModule {}
