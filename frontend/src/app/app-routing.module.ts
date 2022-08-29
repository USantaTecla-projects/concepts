import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/feature/auth.module').then(m => m.AuthPageModule),
  },
  {
    path: 'knowledge',
    loadChildren: () => import('./knowledge/feature/knowledge-manager.module').then(m => m.KnowledgeManagerPageModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
