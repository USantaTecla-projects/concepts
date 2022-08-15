import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PagesModule } from 'src/app/shared/pages/pages.module';
import { CoreModule } from 'src/app/core/core.module';

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, AppRoutingModule, BrowserAnimationsModule, PagesModule, CoreModule],
  exports: [],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
