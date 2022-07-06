import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BillingDetailsComponent } from '../list/billing-details.component';
import { BillingDetailsDetailComponent } from '../detail/billing-details-detail.component';
import { BillingDetailsUpdateComponent } from '../update/billing-details-update.component';
import { BillingDetailsRoutingResolveService } from './billing-details-routing-resolve.service';

const billingDetailsRoute: Routes = [
  {
    path: '',
    component: BillingDetailsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillingDetailsDetailComponent,
    resolve: {
      billingDetails: BillingDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillingDetailsUpdateComponent,
    resolve: {
      billingDetails: BillingDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillingDetailsUpdateComponent,
    resolve: {
      billingDetails: BillingDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(billingDetailsRoute)],
  exports: [RouterModule],
})
export class BillingDetailsRoutingModule {}
