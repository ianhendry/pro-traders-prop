import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITradersChallengeTracker, TradersChallengeTracker } from '../traders-challenge-tracker.model';
import { TradersChallengeTrackerService } from '../service/traders-challenge-tracker.service';

import { TradersChallengeTrackerRoutingResolveService } from './traders-challenge-tracker-routing-resolve.service';

describe('TradersChallengeTracker routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TradersChallengeTrackerRoutingResolveService;
  let service: TradersChallengeTrackerService;
  let resultTradersChallengeTracker: ITradersChallengeTracker | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(TradersChallengeTrackerRoutingResolveService);
    service = TestBed.inject(TradersChallengeTrackerService);
    resultTradersChallengeTracker = undefined;
  });

  describe('resolve', () => {
    it('should return ITradersChallengeTracker returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTradersChallengeTracker = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTradersChallengeTracker).toEqual({ id: 123 });
    });

    it('should return new ITradersChallengeTracker if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTradersChallengeTracker = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTradersChallengeTracker).toEqual(new TradersChallengeTracker());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TradersChallengeTracker })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTradersChallengeTracker = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTradersChallengeTracker).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
