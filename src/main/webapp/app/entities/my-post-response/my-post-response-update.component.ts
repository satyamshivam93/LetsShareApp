import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMyPostResponse, MyPostResponse } from 'app/shared/model/my-post-response.model';
import { MyPostResponseService } from './my-post-response.service';
import { IMyPost } from 'app/shared/model/my-post.model';
import { MyPostService } from 'app/entities/my-post/my-post.service';
import * as moment from 'moment/moment';

@Component({
  selector: 'jhi-my-post-response-update',
  templateUrl: './my-post-response-update.component.html',
})
export class MyPostResponseUpdateComponent implements OnInit {
  isSaving = false;
  myposts: IMyPost[] = [];
  createdDateDp: any;
  modifiedDateDp: any;

  editForm = this.fb.group({
    id: [],
    myPostResponse: [],
    postId: [],
    responderId: [],
    myPostReponseStatus: [],
    createdBy: [],
    createdDate: [],
    modifiedBy: [],
    modifiedDate: [],
    comments: [],
    active: [],
    myPost: [],
  });

  constructor(
    protected myPostResponseService: MyPostResponseService,
    protected myPostService: MyPostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myPostResponse }) => {
      this.updateForm(myPostResponse);

      this.myPostService.query().subscribe((res: HttpResponse<IMyPost[]>) => (this.myposts = res.body || []));
    });
  }

  updateForm(myPostResponse: IMyPostResponse): void {
    this.editForm.patchValue({
      id: myPostResponse.id,
      myPostResponse: myPostResponse.myPostResponse,
      postId: myPostResponse.postId,
      responderId: myPostResponse.responderId,
      myPostReponseStatus: myPostResponse.myPostReponseStatus,
      createdBy: myPostResponse.createdBy,
      createdDate: myPostResponse.createdDate,
      modifiedBy: myPostResponse.modifiedBy,
      modifiedDate: myPostResponse.modifiedDate,
      comments: myPostResponse.comments,
      active: myPostResponse.active,
      myPost: myPostResponse.myPost,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const myPostResponse = this.createFromForm();
    if (myPostResponse.id !== undefined) {
      this.subscribeToSaveResponse(this.myPostResponseService.update(myPostResponse));
    } else {
      this.subscribeToSaveResponse(this.myPostResponseService.create(myPostResponse));
    }
  }

  private createFromForm(): IMyPostResponse {
    return {
      ...new MyPostResponse(),
      id: this.editForm.get(['id'])!.value,
      myPostResponse: this.editForm.get(['myPostResponse'])!.value,
      postId: this.editForm.get(['postId'])!.value,
      responderId: this.editForm.get(['responderId'])!.value,
      myPostReponseStatus: this.editForm.get(['myPostReponseStatus'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: moment(new Date()).format('MM-DD-YYYY'), // this.editForm.get(['createdDate'])!.value,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      modifiedDate: moment(new Date()).format('MM-DD-YYYY'), //this.editForm.get(['modifiedDate'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      active: this.editForm.get(['active'])!.value,
      myPost: this.editForm.get(['myPost'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMyPostResponse>>): void {
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

  trackById(index: number, item: IMyPost): any {
    return item.id;
  }
}
