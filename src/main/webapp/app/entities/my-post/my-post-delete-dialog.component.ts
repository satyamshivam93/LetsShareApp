import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMyPost } from 'app/shared/model/my-post.model';
import { MyPostService } from './my-post.service';

@Component({
  templateUrl: './my-post-delete-dialog.component.html',
})
export class MyPostDeleteDialogComponent {
  myPost?: IMyPost;

  constructor(protected myPostService: MyPostService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.myPostService.delete(id).subscribe(() => {
      this.eventManager.broadcast('myPostListModification');
      this.activeModal.close();
    });
  }
}
