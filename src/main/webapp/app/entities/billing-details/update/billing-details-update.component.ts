import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IBillingDetails, BillingDetails } from '../billing-details.model';
import { BillingDetailsService } from '../service/billing-details.service';

@Component({
  selector: 'jhi-billing-details-update',
  templateUrl: './billing-details-update.component.html',
})
export class BillingDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    contactName: [],
    address1: [],
    address2: [],
    address3: [],
    address4: [],
    address5: [],
    address6: [],
    dialCode: [],
    phoneNumber: [],
    messengerId: [],
    dateAdded: [],
    inActive: [],
    inActiveDate: [],
  });

  constructor(
    protected billingDetailsService: BillingDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billingDetails }) => {
      if (billingDetails.id === undefined) {
        const today = dayjs().startOf('day');
        billingDetails.dateAdded = today;
        billingDetails.inActiveDate = today;
      }

      this.updateForm(billingDetails);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const billingDetails = this.createFromForm();
    if (billingDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.billingDetailsService.update(billingDetails));
    } else {
      this.subscribeToSaveResponse(this.billingDetailsService.create(billingDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(billingDetails: IBillingDetails): void {
    this.editForm.patchValue({
      id: billingDetails.id,
      contactName: billingDetails.contactName,
      address1: billingDetails.address1,
      address2: billingDetails.address2,
      address3: billingDetails.address3,
      address4: billingDetails.address4,
      address5: billingDetails.address5,
      address6: billingDetails.address6,
      dialCode: billingDetails.dialCode,
      phoneNumber: billingDetails.phoneNumber,
      messengerId: billingDetails.messengerId,
      dateAdded: billingDetails.dateAdded ? billingDetails.dateAdded.format(DATE_TIME_FORMAT) : null,
      inActive: billingDetails.inActive,
      inActiveDate: billingDetails.inActiveDate ? billingDetails.inActiveDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IBillingDetails {
    return {
      ...new BillingDetails(),
      id: this.editForm.get(['id'])!.value,
      contactName: this.editForm.get(['contactName'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      address3: this.editForm.get(['address3'])!.value,
      address4: this.editForm.get(['address4'])!.value,
      address5: this.editForm.get(['address5'])!.value,
      address6: this.editForm.get(['address6'])!.value,
      dialCode: this.editForm.get(['dialCode'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      messengerId: this.editForm.get(['messengerId'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value ? dayjs(this.editForm.get(['dateAdded'])!.value, DATE_TIME_FORMAT) : undefined,
      inActive: this.editForm.get(['inActive'])!.value,
      inActiveDate: this.editForm.get(['inActiveDate'])!.value
        ? dayjs(this.editForm.get(['inActiveDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
