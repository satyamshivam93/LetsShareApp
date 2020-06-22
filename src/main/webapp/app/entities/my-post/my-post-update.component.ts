import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMyPost, MyPost } from 'app/shared/model/my-post.model';
import { MyPostService } from './my-post.service';

@Component({
  selector: 'jhi-my-post-update',
  templateUrl: './my-post-update.component.html',
})
export class MyPostUpdateComponent implements OnInit {
  isSaving = false;
  createdDateDp: any;
  modifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    userType: [],
    itemType: [],
    description: [],
    createdBy: [],
    createdDate: [],
    modifiedBy: [],
    modifiedDate: [],
    myPostStatus: [],
    active: [],
  });

  constructor(protected myPostService: MyPostService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myPost }) => {
      this.updateForm(myPost);
    });
  }

  updateForm(myPost: IMyPost): void {
    this.editForm.patchValue({
      id: myPost.id,
      userType: myPost.userType,
      itemType: myPost.itemType,
      description: myPost.description,
      createdBy: myPost.createdBy,
      createdDate: myPost.createdDate,
      modifiedBy: myPost.modifiedBy,
      modifiedDate: myPost.modifiedDate,
      myPostStatus: myPost.myPostStatus,
      active: myPost.active,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const myPost = this.createFromForm();
    if (myPost.id !== undefined) {
      this.subscribeToSaveResponse(this.myPostService.update(myPost));
    } else {
      this.subscribeToSaveResponse(this.myPostService.create(myPost));
    }
  }

  private createFromForm(): IMyPost {
    return {
      ...new MyPost(),
      id: this.editForm.get(['id'])!.value,
      userType: this.editForm.get(['userType'])!.value,
      itemType: this.editForm.get(['itemType'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value,
      myPostStatus: this.editForm.get(['myPostStatus'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMyPost>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
