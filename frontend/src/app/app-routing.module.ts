import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IsLoggedGuard } from './shared/utils/is-logged.guard';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/feature/auth.module').then(m => m.AuthPageModule),
  },
  {
    path: '',
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
    canActivate: [IsLoggedGuard],
  },
  {
    path: 'exam',
    loadChildren: () => import('./exam/feature/exam-shell/exam-shell.module').then(m => m.ExamShellModule),
    canActivate: [IsLoggedGuard],
  },
  {
    path: 'knowledge',
    loadChildren: () => import('./knowledge/feature/knowledge-manager.module').then(m => m.KnowledgeManagerPageModule),
    canActivate: [IsLoggedGuard],
  },
  {
    path: 'correction',
    loadChildren: () =>
      import('./correction/feature/correction-shell/correction-shell.module').then(m => m.CorrectionShellModule),
    canActivate: [IsLoggedGuard],
  },
  {
    path: 'profile',
    loadChildren: () => import('./profile/feature/profile.module').then(m => m.ProfileModule),
    canActivate: [IsLoggedGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
