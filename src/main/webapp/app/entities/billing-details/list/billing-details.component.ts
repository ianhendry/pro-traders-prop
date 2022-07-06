import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBillingDetails } from '../billing-details.model';
import { BillingDetailsService } from '../service/billing-details.service';
import { BillingDetailsDeleteDialogComponent } from '../delete/billing-details-delete-dialog.component';

@Component({
  selector: 'jhi-billing-details',
  templateUrl: './billing-details.component.html',
})
export class BillingDetailsComponent implements OnInit {
  billingDetails?: IBillingDetails[];
  isLoading = false;

  constructor(protected billingDetailsService: BillingDetailsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.billingDetailsService.query().subscribe({
      next: (res: HttpResponse<IBillingDetails[]>) => {
        this.isLoading = false;
        this.billingDetails = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IBillingDetails): number {
    return item.id!;
  }

  delete(billingDetails: IBillingDetails): void {
    const modalRef = this.modalService.open(BillingDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.billingDetails = billingDetails;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
