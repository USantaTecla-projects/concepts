import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IsLoggedGuard } from 'src/core/guards/is-logged.guard';
import { AuthenticationPage } from 'src/shared/pages/authentication/authentication.page';
import { HomePage } from 'src/shared/pages/home/home.page';

const routes: Routes = [
  { path: 'login', component: AuthenticationPage },
  { path: 'home', component: HomePage, canActivate: [IsLoggedGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
