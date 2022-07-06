import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBillingDetails, BillingDetails } from '../billing-details.model';
import { BillingDetailsService } from '../service/billing-details.service';

@Injectable({ providedIn: 'root' })
export class BillingDetailsRoutingResolveService implements Resolve<IBillingDetails> {
  constructor(protected service: BillingDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBillingDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((billingDetails: HttpResponse<BillingDetails>) => {
          if (billingDetails.body) {
            return of(billingDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BillingDetails());
  }
}
