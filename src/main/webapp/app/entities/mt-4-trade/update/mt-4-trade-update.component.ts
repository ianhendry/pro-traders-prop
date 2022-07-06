import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMt4Trade, Mt4Trade } from '../mt-4-trade.model';
import { Mt4TradeService } from '../service/mt-4-trade.service';
import { IMt4Account } from 'app/entities/mt-4-account/mt-4-account.model';
import { Mt4AccountService } from 'app/entities/mt-4-account/service/mt-4-account.service';
import { TRADEDIRECTION } from 'app/entities/enumerations/tradedirection.model';

@Component({
  selector: 'jhi-mt-4-trade-update',
  templateUrl: './mt-4-trade-update.component.html',
})
export class Mt4TradeUpdateComponent implements OnInit {
  isSaving = false;
  tRADEDIRECTIONValues = Object.keys(TRADEDIRECTION);

  mt4AccountsSharedCollection: IMt4Account[] = [];

  editForm = this.fb.group({
    id: [],
    ticket: [null, [Validators.required]],
    openTime: [],
    directionType: [],
    positionSize: [],
    symbol: [],
    openPrice: [],
    stopLossPrice: [],
    takeProfitPrice: [],
    closeTime: [],
    closePrice: [],
    commission: [],
    taxes: [],
    swap: [],
    profit: [],
    mt4Account: [],
  });

  constructor(
    protected mt4TradeService: Mt4TradeService,
    protected mt4AccountService: Mt4AccountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mt4Trade }) => {
      if (mt4Trade.id === undefined) {
        const today = dayjs().startOf('day');
        mt4Trade.openTime = today;
        mt4Trade.closeTime = today;
      }

      this.updateForm(mt4Trade);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mt4Trade = this.createFromForm();
    if (mt4Trade.id !== undefined) {
      this.subscribeToSaveResponse(this.mt4TradeService.update(mt4Trade));
    } else {
      this.subscribeToSaveResponse(this.mt4TradeService.create(mt4Trade));
    }
  }

  trackMt4AccountById(_index: number, item: IMt4Account): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMt4Trade>>): void {
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

  protected updateForm(mt4Trade: IMt4Trade): void {
    this.editForm.patchValue({
      id: mt4Trade.id,
      ticket: mt4Trade.ticket,
      openTime: mt4Trade.openTime ? mt4Trade.openTime.format(DATE_TIME_FORMAT) : null,
      directionType: mt4Trade.directionType,
      positionSize: mt4Trade.positionSize,
      symbol: mt4Trade.symbol,
      openPrice: mt4Trade.openPrice,
      stopLossPrice: mt4Trade.stopLossPrice,
      takeProfitPrice: mt4Trade.takeProfitPrice,
      closeTime: mt4Trade.closeTime ? mt4Trade.closeTime.format(DATE_TIME_FORMAT) : null,
      closePrice: mt4Trade.closePrice,
      commission: mt4Trade.commission,
      taxes: mt4Trade.taxes,
      swap: mt4Trade.swap,
      profit: mt4Trade.profit,
      mt4Account: mt4Trade.mt4Account,
    });

    this.mt4AccountsSharedCollection = this.mt4AccountService.addMt4AccountToCollectionIfMissing(
      this.mt4AccountsSharedCollection,
      mt4Trade.mt4Account
    );
  }

  protected loadRelationshipsOptions(): void {
    this.mt4AccountService
      .query()
      .pipe(map((res: HttpResponse<IMt4Account[]>) => res.body ?? []))
      .pipe(
        map((mt4Accounts: IMt4Account[]) =>
          this.mt4AccountService.addMt4AccountToCollectionIfMissing(mt4Accounts, this.editForm.get('mt4Account')!.value)
        )
      )
      .subscribe((mt4Accounts: IMt4Account[]) => (this.mt4AccountsSharedCollection = mt4Accounts));
  }

  protected createFromForm(): IMt4Trade {
    return {
      ...new Mt4Trade(),
      id: this.editForm.get(['id'])!.value,
      ticket: this.editForm.get(['ticket'])!.value,
      openTime: this.editForm.get(['openTime'])!.value ? dayjs(this.editForm.get(['openTime'])!.value, DATE_TIME_FORMAT) : undefined,
      directionType: this.editForm.get(['directionType'])!.value,
      positionSize: this.editForm.get(['positionSize'])!.value,
      symbol: this.editForm.get(['symbol'])!.value,
      openPrice: this.editForm.get(['openPrice'])!.value,
      stopLossPrice: this.editForm.get(['stopLossPrice'])!.value,
      takeProfitPrice: this.editForm.get(['takeProfitPrice'])!.value,
      closeTime: this.editForm.get(['closeTime'])!.value ? dayjs(this.editForm.get(['closeTime'])!.value, DATE_TIME_FORMAT) : undefined,
      closePrice: this.editForm.get(['closePrice'])!.value,
      commission: this.editForm.get(['commission'])!.value,
      taxes: this.editForm.get(['taxes'])!.value,
      swap: this.editForm.get(['swap'])!.value,
      profit: this.editForm.get(['profit'])!.value,
      mt4Account: this.editForm.get(['mt4Account'])!.value,
    };
  }
}
