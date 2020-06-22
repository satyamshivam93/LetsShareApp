import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMyPostResponse } from 'app/shared/model/my-post-response.model';

type EntityResponseType = HttpResponse<IMyPostResponse>;
type EntityArrayResponseType = HttpResponse<IMyPostResponse[]>;

@Injectable({ providedIn: 'root' })
export class MyPostResponseService {
  public resourceUrl = SERVER_API_URL + 'api/my-post-responses';

  constructor(protected http: HttpClient) {}

  create(myPostResponse: IMyPostResponse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(myPostResponse);
    return this.http
      .post<IMyPostResponse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(myPostResponse: IMyPostResponse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(myPostResponse);
    return this.http
      .put<IMyPostResponse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMyPostResponse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMyPostResponse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(myPostResponse: IMyPostResponse): IMyPostResponse {
    const copy: IMyPostResponse = Object.assign({}, myPostResponse, {
      createdDate:
        myPostResponse.createdDate && myPostResponse.createdDate.isValid() ? myPostResponse.createdDate.format(DATE_FORMAT) : undefined,
      modifiedDate:
        myPostResponse.modifiedDate && myPostResponse.modifiedDate.isValid() ? myPostResponse.modifiedDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((myPostResponse: IMyPostResponse) => {
        myPostResponse.createdDate = myPostResponse.createdDate ? moment(myPostResponse.createdDate) : undefined;
        myPostResponse.modifiedDate = myPostResponse.modifiedDate ? moment(myPostResponse.modifiedDate) : undefined;
      });
    }
    return res;
  }
}
