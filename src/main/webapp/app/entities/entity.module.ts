import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'my-post',
        loadChildren: () => import('./my-post/my-post.module').then(m => m.LetsShareAppMyPostModule),
      },
      {
        path: 'my-post-response',
        loadChildren: () => import('./my-post-response/my-post-response.module').then(m => m.LetsShareAppMyPostResponseModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LetsShareAppEntityModule {}
