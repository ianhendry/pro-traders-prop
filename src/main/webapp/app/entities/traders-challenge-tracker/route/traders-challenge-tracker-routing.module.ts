import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TradersChallengeTrackerComponent } from '../list/traders-challenge-tracker.component';
import { TradersChallengeTrackerDetailComponent } from '../detail/traders-challenge-tracker-detail.component';
import { TradersChallengeTrackerUpdateComponent } from '../update/traders-challenge-tracker-update.component';
import { TradersChallengeTrackerRoutingResolveService } from './traders-challenge-tracker-routing-resolve.service';

const tradersChallengeTrackerRoute: Routes = [
  {
    path: '',
    component: TradersChallengeTrackerComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TradersChallengeTrackerDetailComponent,
    resolve: {
      tradersChallengeTracker: TradersChallengeTrackerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TradersChallengeTrackerUpdateComponent,
    resolve: {
      tradersChallengeTracker: TradersChallengeTrackerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TradersChallengeTrackerUpdateComponent,
    resolve: {
      tradersChallengeTracker: TradersChallengeTrackerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tradersChallengeTrackerRoute)],
  exports: [RouterModule],
})
export class TradersChallengeTrackerRoutingModule {}
