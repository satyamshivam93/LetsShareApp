import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMyPostResponse, MyPostResponse } from 'app/shared/model/my-post-response.model';
import { MyPostResponseService } from './my-post-response.service';
import { MyPostResponseComponent } from './my-post-response.component';
import { MyPostResponseDetailComponent } from './my-post-response-detail.component';
import { MyPostResponseUpdateComponent } from './my-post-response-update.component';

@Injectable({ providedIn: 'root' })
export class MyPostResponseResolve implements Resolve<IMyPostResponse> {
  constructor(private service: MyPostResponseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMyPostResponse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((myPostResponse: HttpResponse<MyPostResponse>) => {
          if (myPostResponse.body) {
            return of(myPostResponse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MyPostResponse());
  }
}

export const myPostResponseRoute: Routes = [
  {
    path: '',
    component: MyPostResponseComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MyPostResponses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MyPostResponseDetailComponent,
    resolve: {
      myPostResponse: MyPostResponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MyPostResponses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MyPostResponseUpdateComponent,
    resolve: {
      myPostResponse: MyPostResponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MyPostResponses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MyPostResponseUpdateComponent,
    resolve: {
      myPostResponse: MyPostResponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MyPostResponses',
    },
    canActivate: [UserRouteAccessService],
  },
];
