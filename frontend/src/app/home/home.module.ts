import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { HomeRoutingModule } from './home-routing.module';
import { HomePage } from './home.page';

@NgModule({
  imports: [CommonModule, SharedModule, HomeRoutingModule],
  declarations: [HomePage],
})
export class HomeModule {}
