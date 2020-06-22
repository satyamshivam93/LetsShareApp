import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMyPost } from 'app/shared/model/my-post.model';

type EntityResponseType = HttpResponse<IMyPost>;
type EntityArrayResponseType = HttpResponse<IMyPost[]>;

@Injectable({ providedIn: 'root' })
export class MyPostService {
  public resourceUrl = SERVER_API_URL + 'api/my-posts';

  constructor(protected http: HttpClient) {}

  create(myPost: IMyPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(myPost);
    return this.http
      .post<IMyPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(myPost: IMyPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(myPost);
    return this.http
      .put<IMyPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMyPost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMyPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(myPost: IMyPost): IMyPost {
    const copy: IMyPost = Object.assign({}, myPost, {
      createdDate: myPost.createdDate && myPost.createdDate.isValid() ? myPost.createdDate.format(DATE_FORMAT) : undefined,
      modifiedDate: myPost.modifiedDate && myPost.modifiedDate.isValid() ? myPost.modifiedDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.modifiedDate = res.body.modifiedDate ? moment(res.body.modifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((myPost: IMyPost) => {
        myPost.createdDate = myPost.createdDate ? moment(myPost.createdDate) : undefined;
        myPost.modifiedDate = myPost.modifiedDate ? moment(myPost.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
