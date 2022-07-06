import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillingDetails } from '../billing-details.model';

@Component({
  selector: 'jhi-billing-details-detail',
  templateUrl: './billing-details-detail.component.html',
})
export class BillingDetailsDetailComponent implements OnInit {
  billingDetails: IBillingDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billingDetails }) => {
      this.billingDetails = billingDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
