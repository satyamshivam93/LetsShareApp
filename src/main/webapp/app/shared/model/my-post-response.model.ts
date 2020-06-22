import { Moment } from 'moment';
import { IMyPost } from 'app/shared/model/my-post.model';
import { MyPostReponseStatus } from 'app/shared/model/enumerations/my-post-reponse-status.model';

export interface IMyPostResponse {
  id?: number;
  myPostResponse?: string;
  postId?: number;
  responderId?: number;
  myPostReponseStatus?: MyPostReponseStatus;
  createdBy?: number;
  createdDate?: Moment;
  modifiedBy?: number;
  modifiedDate?: Moment;
  comments?: string;
  active?: boolean;
  myPost?: IMyPost;
}

export class MyPostResponse implements IMyPostResponse {
  constructor(
    public id?: number,
    public myPostResponse?: string,
    public postId?: number,
    public responderId?: number,
    public myPostReponseStatus?: MyPostReponseStatus,
    public createdBy?: number,
    public createdDate?: Moment,
    public modifiedBy?: number,
    public modifiedDate?: Moment,
    public comments?: string,
    public active?: boolean,
    public myPost?: IMyPost
  ) {
    this.active = this.active || false;
  }
}
