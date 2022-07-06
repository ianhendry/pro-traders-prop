import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBillingDetails, BillingDetails } from '../billing-details.model';

import { BillingDetailsService } from './billing-details.service';

describe('BillingDetails Service', () => {
  let service: BillingDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IBillingDetails;
  let expectedResult: IBillingDetails | IBillingDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BillingDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      contactName: 'AAAAAAA',
      address1: 'AAAAAAA',
      address2: 'AAAAAAA',
      address3: 'AAAAAAA',
      address4: 'AAAAAAA',
      address5: 'AAAAAAA',
      address6: 'AAAAAAA',
      dialCode: 'AAAAAAA',
      phoneNumber: 'AAAAAAA',
      messengerId: 'AAAAAAA',
      dateAdded: currentDate,
      inActive: false,
      inActiveDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
          inActiveDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a BillingDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
          inActiveDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAdded: currentDate,
          inActiveDate: currentDate,
        },
        returnedFromService
      );

      service.create(new BillingDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BillingDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          contactName: 'BBBBBB',
          address1: 'BBBBBB',
          address2: 'BBBBBB',
          address3: 'BBBBBB',
          address4: 'BBBBBB',
          address5: 'BBBBBB',
          address6: 'BBBBBB',
          dialCode: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          messengerId: 'BBBBBB',
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
          inActive: true,
          inActiveDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAdded: currentDate,
          inActiveDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BillingDetails', () => {
      const patchObject = Object.assign(
        {
          address1: 'BBBBBB',
          address3: 'BBBBBB',
          address6: 'BBBBBB',
          dialCode: 'BBBBBB',
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
          inActive: true,
          inActiveDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new BillingDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateAdded: currentDate,
          inActiveDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BillingDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          contactName: 'BBBBBB',
          address1: 'BBBBBB',
          address2: 'BBBBBB',
          address3: 'BBBBBB',
          address4: 'BBBBBB',
          address5: 'BBBBBB',
          address6: 'BBBBBB',
          dialCode: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          messengerId: 'BBBBBB',
          dateAdded: currentDate.format(DATE_TIME_FORMAT),
          inActive: true,
          inActiveDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAdded: currentDate,
          inActiveDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BillingDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBillingDetailsToCollectionIfMissing', () => {
      it('should add a BillingDetails to an empty array', () => {
        const billingDetails: IBillingDetails = { id: 123 };
        expectedResult = service.addBillingDetailsToCollectionIfMissing([], billingDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(billingDetails);
      });

      it('should not add a BillingDetails to an array that contains it', () => {
        const billingDetails: IBillingDetails = { id: 123 };
        const billingDetailsCollection: IBillingDetails[] = [
          {
            ...billingDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addBillingDetailsToCollectionIfMissing(billingDetailsCollection, billingDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BillingDetails to an array that doesn't contain it", () => {
        const billingDetails: IBillingDetails = { id: 123 };
        const billingDetailsCollection: IBillingDetails[] = [{ id: 456 }];
        expectedResult = service.addBillingDetailsToCollectionIfMissing(billingDetailsCollection, billingDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(billingDetails);
      });

      it('should add only unique BillingDetails to an array', () => {
        const billingDetailsArray: IBillingDetails[] = [{ id: 123 }, { id: 456 }, { id: 14184 }];
        const billingDetailsCollection: IBillingDetails[] = [{ id: 123 }];
        expectedResult = service.addBillingDetailsToCollectionIfMissing(billingDetailsCollection, ...billingDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const billingDetails: IBillingDetails = { id: 123 };
        const billingDetails2: IBillingDetails = { id: 456 };
        expectedResult = service.addBillingDetailsToCollectionIfMissing([], billingDetails, billingDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(billingDetails);
        expect(expectedResult).toContain(billingDetails2);
      });

      it('should accept null and undefined values', () => {
        const billingDetails: IBillingDetails = { id: 123 };
        expectedResult = service.addBillingDetailsToCollectionIfMissing([], null, billingDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(billingDetails);
      });

      it('should return initial array if no BillingDetails is added', () => {
        const billingDetailsCollection: IBillingDetails[] = [{ id: 123 }];
        expectedResult = service.addBillingDetailsToCollectionIfMissing(billingDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(billingDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
