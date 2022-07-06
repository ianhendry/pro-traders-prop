import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserNotification, UserNotification } from '../user-notification.model';
import { UserNotificationService } from '../service/user-notification.service';

@Injectable({ providedIn: 'root' })
export class UserNotificationRoutingResolveService implements Resolve<IUserNotification> {
  constructor(protected service: UserNotificationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserNotification> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userNotification: HttpResponse<UserNotification>) => {
          if (userNotification.body) {
            return of(userNotification.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserNotification());
  }
}
