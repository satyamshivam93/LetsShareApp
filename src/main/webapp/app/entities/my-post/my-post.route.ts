import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMyPost, MyPost } from 'app/shared/model/my-post.model';
import { MyPostService } from './my-post.service';
import { MyPostComponent } from './my-post.component';
import { MyPostDetailComponent } from './my-post-detail.component';
import { MyPostUpdateComponent } from './my-post-update.component';

@Injectable({ providedIn: 'root' })
export class MyPostResolve implements Resolve<IMyPost> {
  constructor(private service: MyPostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMyPost> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((myPost: HttpResponse<MyPost>) => {
          if (myPost.body) {
            return of(myPost.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MyPost());
  }
}

export const myPostRoute: Routes = [
  {
    path: '',
    component: MyPostComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MyPosts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MyPostDetailComponent,
    resolve: {
      myPost: MyPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MyPosts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MyPostUpdateComponent,
    resolve: {
      myPost: MyPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MyPosts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MyPostUpdateComponent,
    resolve: {
      myPost: MyPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MyPosts',
    },
    canActivate: [UserRouteAccessService],
  },
];
