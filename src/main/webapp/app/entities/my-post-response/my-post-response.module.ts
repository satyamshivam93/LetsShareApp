import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LetsShareAppSharedModule } from 'app/shared/shared.module';
import { MyPostResponseComponent } from './my-post-response.component';
import { MyPostResponseDetailComponent } from './my-post-response-detail.component';
import { MyPostResponseUpdateComponent } from './my-post-response-update.component';
import { MyPostResponseDeleteDialogComponent } from './my-post-response-delete-dialog.component';
import { myPostResponseRoute } from './my-post-response.route';

@NgModule({
  imports: [LetsShareAppSharedModule, RouterModule.forChild(myPostResponseRoute)],
  declarations: [
    MyPostResponseComponent,
    MyPostResponseDetailComponent,
    MyPostResponseUpdateComponent,
    MyPostResponseDeleteDialogComponent,
  ],
  entryComponents: [MyPostResponseDeleteDialogComponent],
})
export class LetsShareAppMyPostResponseModule {}
