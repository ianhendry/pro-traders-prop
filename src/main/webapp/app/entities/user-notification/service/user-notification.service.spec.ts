import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUserNotification, UserNotification } from '../user-notification.model';

import { UserNotificationService } from './user-notification.service';

describe('UserNotification Service', () => {
  let service: UserNotificationService;
  let httpMock: HttpTestingController;
  let elemDefault: IUserNotification;
  let expectedResult: IUserNotification | IUserNotification[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserNotificationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      commentTitle: 'AAAAAAA',
      commentBody: 'AAAAAAA',
      commentMediaContentType: 'image/png',
      commentMedia: 'AAAAAAA',
      dateAdded: currentDate,
      makePublicVisibleOnSite: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a UserNotification', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAdded: currentDate,
        },
        returnedFromService
      );

      service.create(new UserNotification()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserNotification', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          commentTitle: 'BBBBBB',
          commentBody: 'BBBBBB',
          commentMedia: 'BBBBBB',
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
          makePublicVisibleOnSite: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAdded: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserNotification', () => {
      const patchObject = Object.assign(
        {
          commentTitle: 'BBBBBB',
          commentMedia: 'BBBBBB',
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
          makePublicVisibleOnSite: true,
        },
        new UserNotification()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateAdded: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserNotification', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          commentTitle: 'BBBBBB',
          commentBody: 'BBBBBB',
          commentMedia: 'BBBBBB',
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
          makePublicVisibleOnSite: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAdded: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a UserNotification', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addUserNotificationToCollectionIfMissing', () => {
      it('should add a UserNotification to an empty array', () => {
        const userNotification: IUserNotification = { id: 123 };
        expectedResult = service.addUserNotificationToCollectionIfMissing([], userNotification);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userNotification);
      });

      it('should not add a UserNotification to an array that contains it', () => {
        const userNotification: IUserNotification = { id: 123 };
        const userNotificationCollection: IUserNotification[] = [
          {
            ...userNotification,
          },
          { id: 456 },
        ];
        expectedResult = service.addUserNotificationToCollectionIfMissing(userNotificationCollection, userNotification);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserNotification to an array that doesn't contain it", () => {
        const userNotification: IUserNotification = { id: 123 };
        const userNotificationCollection: IUserNotification[] = [{ id: 456 }];
        expectedResult = service.addUserNotificationToCollectionIfMissing(userNotificationCollection, userNotification);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userNotification);
      });

      it('should add only unique UserNotification to an array', () => {
        const userNotificationArray: IUserNotification[] = [{ id: 123 }, { id: 456 }, { id: 27925 }];
        const userNotificationCollection: IUserNotification[] = [{ id: 123 }];
        expectedResult = service.addUserNotificationToCollectionIfMissing(userNotificationCollection, ...userNotificationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userNotification: IUserNotification = { id: 123 };
        const userNotification2: IUserNotification = { id: 456 };
        expectedResult = service.addUserNotificationToCollectionIfMissing([], userNotification, userNotification2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userNotification);
        expect(expectedResult).toContain(userNotification2);
      });

      it('should accept null and undefined values', () => {
        const userNotification: IUserNotification = { id: 123 };
        expectedResult = service.addUserNotificationToCollectionIfMissing([], null, userNotification, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userNotification);
      });

      it('should return initial array if no UserNotification is added', () => {
        const userNotificationCollection: IUserNotification[] = [{ id: 123 }];
        expectedResult = service.addUserNotificationToCollectionIfMissing(userNotificationCollection, undefined, null);
        expect(expectedResult).toEqual(userNotificationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
