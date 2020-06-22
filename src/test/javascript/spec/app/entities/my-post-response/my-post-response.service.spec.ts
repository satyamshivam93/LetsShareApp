import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MyPostResponseService } from 'app/entities/my-post-response/my-post-response.service';
import { IMyPostResponse, MyPostResponse } from 'app/shared/model/my-post-response.model';
import { MyPostReponseStatus } from 'app/shared/model/enumerations/my-post-reponse-status.model';

describe('Service Tests', () => {
  describe('MyPostResponse Service', () => {
    let injector: TestBed;
    let service: MyPostResponseService;
    let httpMock: HttpTestingController;
    let elemDefault: IMyPostResponse;
    let expectedResult: IMyPostResponse | IMyPostResponse[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MyPostResponseService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MyPostResponse(0, 'AAAAAAA', 0, 0, MyPostReponseStatus.APPROVED, 0, currentDate, 0, currentDate, 'AAAAAAA', false);
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

      it('should create a MyPostResponse', () => {
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

        service.create(new MyPostResponse()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MyPostResponse', () => {
        const returnedFromService = Object.assign(
          {
            myPostResponse: 'BBBBBB',
            postId: 1,
            responderId: 1,
            myPostReponseStatus: 'BBBBBB',
            createdBy: 1,
            createdDate: currentDate.format(DATE_FORMAT),
            modifiedBy: 1,
            modifiedDate: currentDate.format(DATE_FORMAT),
            comments: 'BBBBBB',
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

      it('should return a list of MyPostResponse', () => {
        const returnedFromService = Object.assign(
          {
            myPostResponse: 'BBBBBB',
            postId: 1,
            responderId: 1,
            myPostReponseStatus: 'BBBBBB',
            createdBy: 1,
            createdDate: currentDate.format(DATE_FORMAT),
            modifiedBy: 1,
            modifiedDate: currentDate.format(DATE_FORMAT),
            comments: 'BBBBBB',
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

      it('should delete a MyPostResponse', () => {
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
