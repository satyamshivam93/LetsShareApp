import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMyPost } from 'app/shared/model/my-post.model';

@Component({
  selector: 'jhi-my-post-detail',
  templateUrl: './my-post-detail.component.html',
})
export class MyPostDetailComponent implements OnInit {
  myPost: IMyPost | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myPost }) => (this.myPost = myPost));
  }

  previousState(): void {
    window.history.back();
  }
}
