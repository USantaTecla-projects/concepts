import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './shared/utils/auth.guard';
import { RoleGuard } from './shared/utils/role.guard';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/feature/auth.module').then(m => m.AuthPageModule),
  },
  {
    path: '',
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'exam',
    loadChildren: () => import('./exam/feature/exam-shell/exam-shell.module').then(m => m.ExamShellModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'knowledge',
    loadChildren: () => import('./knowledge/feature/knowledge-manager.module').then(m => m.KnowledgeManagerPageModule),
    canActivate: [AuthGuard, RoleGuard],
    data: {
      role: 'TEACHER',
    },
  },
  {
    path: 'correction',
    loadChildren: () =>
      import('./correction/feature/correction-shell/correction-shell.module').then(m => m.CorrectionShellModule),
    canActivate: [AuthGuard, RoleGuard],
    data: {
      role: 'TEACHER',
    },
  },
  {
    path: 'profile',
    loadChildren: () => import('./profile/feature/profile.module').then(m => m.ProfileModule),
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
