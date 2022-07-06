import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITradersChallengeTracker, TradersChallengeTracker } from '../traders-challenge-tracker.model';

import { TradersChallengeTrackerService } from './traders-challenge-tracker.service';

describe('TradersChallengeTracker Service', () => {
  let service: TradersChallengeTrackerService;
  let httpMock: HttpTestingController;
  let elemDefault: ITradersChallengeTracker;
  let expectedResult: ITradersChallengeTracker | ITradersChallengeTracker[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TradersChallengeTrackerService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      tradeChallengeName: 'AAAAAAA',
      startDate: currentDate,
      accountDayStartBalance: 0,
      accountDayStartEquity: 0,
      runningMaxTotalDrawdown: 0,
      runningMaxDailyDrawdown: 0,
      lowestDrawdownPoint: 0,
      rulesViolated: false,
      ruleViolated: 'AAAAAAA',
      ruleViolatedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          startDate: currentDate.format(DATE_TIME_FORMAT),
          ruleViolatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TradersChallengeTracker', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          startDate: currentDate.format(DATE_TIME_FORMAT),
          ruleViolatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          ruleViolatedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new TradersChallengeTracker()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TradersChallengeTracker', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tradeChallengeName: 'BBBBBB',
          startDate: currentDate.format(DATE_TIME_FORMAT),
          accountDayStartBalance: 1,
          accountDayStartEquity: 1,
          runningMaxTotalDrawdown: 1,
          runningMaxDailyDrawdown: 1,
          lowestDrawdownPoint: 1,
          rulesViolated: true,
          ruleViolated: 'BBBBBB',
          ruleViolatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          ruleViolatedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TradersChallengeTracker', () => {
      const patchObject = Object.assign(
        {
          tradeChallengeName: 'BBBBBB',
          startDate: currentDate.format(DATE_TIME_FORMAT),
          accountDayStartBalance: 1,
          runningMaxTotalDrawdown: 1,
          runningMaxDailyDrawdown: 1,
          rulesViolated: true,
          ruleViolated: 'BBBBBB',
          ruleViolatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new TradersChallengeTracker()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          startDate: currentDate,
          ruleViolatedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TradersChallengeTracker', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tradeChallengeName: 'BBBBBB',
          startDate: currentDate.format(DATE_TIME_FORMAT),
          accountDayStartBalance: 1,
          accountDayStartEquity: 1,
          runningMaxTotalDrawdown: 1,
          runningMaxDailyDrawdown: 1,
          lowestDrawdownPoint: 1,
          rulesViolated: true,
          ruleViolated: 'BBBBBB',
          ruleViolatedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startDate: currentDate,
          ruleViolatedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TradersChallengeTracker', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTradersChallengeTrackerToCollectionIfMissing', () => {
      it('should add a TradersChallengeTracker to an empty array', () => {
        const tradersChallengeTracker: ITradersChallengeTracker = { id: 123 };
        expectedResult = service.addTradersChallengeTrackerToCollectionIfMissing([], tradersChallengeTracker);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tradersChallengeTracker);
      });

      it('should not add a TradersChallengeTracker to an array that contains it', () => {
        const tradersChallengeTracker: ITradersChallengeTracker = { id: 123 };
        const tradersChallengeTrackerCollection: ITradersChallengeTracker[] = [
          {
            ...tradersChallengeTracker,
          },
          { id: 456 },
        ];
        expectedResult = service.addTradersChallengeTrackerToCollectionIfMissing(
          tradersChallengeTrackerCollection,
          tradersChallengeTracker
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TradersChallengeTracker to an array that doesn't contain it", () => {
        const tradersChallengeTracker: ITradersChallengeTracker = { id: 123 };
        const tradersChallengeTrackerCollection: ITradersChallengeTracker[] = [{ id: 456 }];
        expectedResult = service.addTradersChallengeTrackerToCollectionIfMissing(
          tradersChallengeTrackerCollection,
          tradersChallengeTracker
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tradersChallengeTracker);
      });

      it('should add only unique TradersChallengeTracker to an array', () => {
        const tradersChallengeTrackerArray: ITradersChallengeTracker[] = [{ id: 123 }, { id: 456 }, { id: 70409 }];
        const tradersChallengeTrackerCollection: ITradersChallengeTracker[] = [{ id: 123 }];
        expectedResult = service.addTradersChallengeTrackerToCollectionIfMissing(
          tradersChallengeTrackerCollection,
          ...tradersChallengeTrackerArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tradersChallengeTracker: ITradersChallengeTracker = { id: 123 };
        const tradersChallengeTracker2: ITradersChallengeTracker = { id: 456 };
        expectedResult = service.addTradersChallengeTrackerToCollectionIfMissing([], tradersChallengeTracker, tradersChallengeTracker2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tradersChallengeTracker);
        expect(expectedResult).toContain(tradersChallengeTracker2);
      });

      it('should accept null and undefined values', () => {
        const tradersChallengeTracker: ITradersChallengeTracker = { id: 123 };
        expectedResult = service.addTradersChallengeTrackerToCollectionIfMissing([], null, tradersChallengeTracker, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tradersChallengeTracker);
      });

      it('should return initial array if no TradersChallengeTracker is added', () => {
        const tradersChallengeTrackerCollection: ITradersChallengeTracker[] = [{ id: 123 }];
        expectedResult = service.addTradersChallengeTrackerToCollectionIfMissing(tradersChallengeTrackerCollection, undefined, null);
        expect(expectedResult).toEqual(tradersChallengeTrackerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
