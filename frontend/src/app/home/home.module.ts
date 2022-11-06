import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from '../shared/material.module';
import { HomeRoutingModule } from './home-routing.module';
import { HomePage } from './home.page';

@NgModule({
  imports: [CommonModule, MaterialModule, HomeRoutingModule],
  declarations: [HomePage],
})
export class HomeModule {}
