import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LetsShareAppSharedModule } from 'app/shared/shared.module';
import { MyPostComponent } from './my-post.component';
import { MyPostDetailComponent } from './my-post-detail.component';
import { MyPostUpdateComponent } from './my-post-update.component';
import { MyPostDeleteDialogComponent } from './my-post-delete-dialog.component';
import { myPostRoute } from './my-post.route';

@NgModule({
  imports: [LetsShareAppSharedModule, RouterModule.forChild(myPostRoute)],
  declarations: [MyPostComponent, MyPostDetailComponent, MyPostUpdateComponent, MyPostDeleteDialogComponent],
  entryComponents: [MyPostDeleteDialogComponent],
})
export class LetsShareAppMyPostModule {}
