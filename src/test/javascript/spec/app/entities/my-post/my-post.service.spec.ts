import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MyPostService } from 'app/entities/my-post/my-post.service';
import { IMyPost, MyPost } from 'app/shared/model/my-post.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';
import { ItemType } from 'app/shared/model/enumerations/item-type.model';
import { MyPostStatus } from 'app/shared/model/enumerations/my-post-status.model';

describe('Service Tests', () => {
  describe('MyPost Service', () => {
    let injector: TestBed;
    let service: MyPostService;
    let httpMock: HttpTestingController;
    let elemDefault: IMyPost;
    let expectedResult: IMyPost | IMyPost[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MyPostService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MyPost(0, UserType.DONOR, ItemType.BOOKS, 'AAAAAAA', 0, currentDate, 0, currentDate, MyPostStatus.AVAILABLE, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdDate: currentDate.format(DATE_FORMAT),
            modifiedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MyPost', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate.format(DATE_FORMAT),
            modifiedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new MyPost()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MyPost', () => {
        const returnedFromService = Object.assign(
          {
            userType: 'BBBBBB',
            itemType: 'BBBBBB',
            description: 'BBBBBB',
            createdBy: 1,
            createdDate: currentDate.format(DATE_FORMAT),
            modifiedBy: 1,
            modifiedDate: currentDate.format(DATE_FORMAT),
            myPostStatus: 'BBBBBB',
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MyPost', () => {
        const returnedFromService = Object.assign(
          {
            userType: 'BBBBBB',
            itemType: 'BBBBBB',
            description: 'BBBBBB',
            createdBy: 1,
            createdDate: currentDate.format(DATE_FORMAT),
            modifiedBy: 1,
            modifiedDate: currentDate.format(DATE_FORMAT),
            myPostStatus: 'BBBBBB',
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            modifiedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MyPost', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
