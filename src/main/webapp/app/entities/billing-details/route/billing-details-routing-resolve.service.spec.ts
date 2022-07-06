import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBillingDetails, BillingDetails } from '../billing-details.model';
import { BillingDetailsService } from '../service/billing-details.service';

import { BillingDetailsRoutingResolveService } from './billing-details-routing-resolve.service';

describe('BillingDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BillingDetailsRoutingResolveService;
  let service: BillingDetailsService;
  let resultBillingDetails: IBillingDetails | undefined;

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
    routingResolveService = TestBed.inject(BillingDetailsRoutingResolveService);
    service = TestBed.inject(BillingDetailsService);
    resultBillingDetails = undefined;
  });

  describe('resolve', () => {
    it('should return IBillingDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBillingDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBillingDetails).toEqual({ id: 123 });
    });

    it('should return new IBillingDetails if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBillingDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBillingDetails).toEqual(new BillingDetails());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BillingDetails })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBillingDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBillingDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
