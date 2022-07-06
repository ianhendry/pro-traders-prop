import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BillingDetailsComponent } from './list/billing-details.component';
import { BillingDetailsDetailComponent } from './detail/billing-details-detail.component';
import { BillingDetailsUpdateComponent } from './update/billing-details-update.component';
import { BillingDetailsDeleteDialogComponent } from './delete/billing-details-delete-dialog.component';
import { BillingDetailsRoutingModule } from './route/billing-details-routing.module';

@NgModule({
  imports: [SharedModule, BillingDetailsRoutingModule],
  declarations: [
    BillingDetailsComponent,
    BillingDetailsDetailComponent,
    BillingDetailsUpdateComponent,
    BillingDetailsDeleteDialogComponent,
  ],
  entryComponents: [BillingDetailsDeleteDialogComponent],
})
export class BillingDetailsModule {}
