import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMyPostResponse } from 'app/shared/model/my-post-response.model';
import { MyPostResponseService } from './my-post-response.service';

@Component({
  templateUrl: './my-post-response-delete-dialog.component.html',
})
export class MyPostResponseDeleteDialogComponent {
  myPostResponse?: IMyPostResponse;

  constructor(
    protected myPostResponseService: MyPostResponseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.myPostResponseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('myPostResponseListModification');
      this.activeModal.close();
    });
  }
}
