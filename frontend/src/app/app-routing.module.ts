import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/feature/auth.module').then(m => m.AuthPageModule),
  },
  {
    path: 'knowledge',
    loadChildren: () => import('./knowledge/feature/knowledge-manager.module').then(m => m.KnowledgeManagerPageModule),
  },
  {
    path: 'exam',
    loadChildren: () => import('./exam/feature/exam-shell/exam-shell.module').then(m => m.ExamShellModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
