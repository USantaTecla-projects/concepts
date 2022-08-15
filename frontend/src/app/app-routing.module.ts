import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IsLoggedGuard } from 'src/app/core/guards/is-logged.guard';
import { AuthenticationPage } from 'src/app/shared/pages/authentication/authentication.page';
import { HomePage } from 'src/app/shared/pages/home/home.page';
import { ConceptsComponent } from './shared/pages/concepts/concepts.page';

const routes: Routes = [
  { path: '', component: ConceptsComponent, canActivate: [IsLoggedGuard] },
  { path: 'auth', component: AuthenticationPage },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
