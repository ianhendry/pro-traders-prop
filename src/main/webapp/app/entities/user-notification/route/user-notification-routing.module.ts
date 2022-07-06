import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserNotificationComponent } from '../list/user-notification.component';
import { UserNotificationDetailComponent } from '../detail/user-notification-detail.component';
import { UserNotificationUpdateComponent } from '../update/user-notification-update.component';
import { UserNotificationRoutingResolveService } from './user-notification-routing-resolve.service';

const userNotificationRoute: Routes = [
  {
    path: '',
    component: UserNotificationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserNotificationDetailComponent,
    resolve: {
      userNotification: UserNotificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserNotificationUpdateComponent,
    resolve: {
      userNotification: UserNotificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserNotificationUpdateComponent,
    resolve: {
      userNotification: UserNotificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userNotificationRoute)],
  exports: [RouterModule],
})
export class UserNotificationRoutingModule {}
