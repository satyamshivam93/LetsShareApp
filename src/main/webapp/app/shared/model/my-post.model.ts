import { Moment } from 'moment';
import { IMyPostResponse } from 'app/shared/model/my-post-response.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';
import { ItemType } from 'app/shared/model/enumerations/item-type.model';
import { MyPostStatus } from 'app/shared/model/enumerations/my-post-status.model';

export interface IMyPost {
  id?: number;
  userType?: UserType;
  itemType?: ItemType;
  description?: string;
  createdBy?: number;
  createdDate?: Moment;
  modifiedBy?: number;
  modifiedDate?: Moment;
  myPostStatus?: MyPostStatus;
  active?: boolean;
  myPostResponses?: IMyPostResponse[];
}

export class MyPost implements IMyPost {
  constructor(
    public id?: number,
    public userType?: UserType,
    public itemType?: ItemType,
    public description?: string,
    public createdBy?: number,
    public createdDate?: Moment,
    public modifiedBy?: number,
    public modifiedDate?: Moment,
    public myPostStatus?: MyPostStatus,
    public active?: boolean,
    public myPostResponses?: IMyPostResponse[]
  ) {
    this.active = this.active || false;
  }
}
