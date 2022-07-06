import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBillingDetails } from '../billing-details.model';
import { BillingDetailsService } from '../service/billing-details.service';

@Component({
  templateUrl: './billing-details-delete-dialog.component.html',
})
export class BillingDetailsDeleteDialogComponent {
  billingDetails?: IBillingDetails;

  constructor(protected billingDetailsService: BillingDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.billingDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
