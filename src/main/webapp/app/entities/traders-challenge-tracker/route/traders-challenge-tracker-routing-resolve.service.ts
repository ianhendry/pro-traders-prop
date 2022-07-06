import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITradersChallengeTracker, TradersChallengeTracker } from '../traders-challenge-tracker.model';
import { TradersChallengeTrackerService } from '../service/traders-challenge-tracker.service';

@Injectable({ providedIn: 'root' })
export class TradersChallengeTrackerRoutingResolveService implements Resolve<ITradersChallengeTracker> {
  constructor(protected service: TradersChallengeTrackerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITradersChallengeTracker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tradersChallengeTracker: HttpResponse<TradersChallengeTracker>) => {
          if (tradersChallengeTracker.body) {
            return of(tradersChallengeTracker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TradersChallengeTracker());
  }
}
