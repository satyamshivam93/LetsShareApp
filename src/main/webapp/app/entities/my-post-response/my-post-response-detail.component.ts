import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMyPostResponse } from '/app/shared/model/my-post-response.model';
//import { Account, Principal } from '../.app/core';

@Component({
  selector: 'jhi-my-post-response-detail',
  templateUrl: './my-post-response-detail.component.html',
})
export class MyPostResponseDetailComponent implements OnInit {
  myPostResponse: IMyPostResponse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myPostResponse }) => (this.myPostResponse = myPostResponse));
  }

  previousState(): void {
    window.history.back();
  }
}
