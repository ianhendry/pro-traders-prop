import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITradeChallenge, TradeChallenge } from '../trade-challenge.model';
import { TradeChallengeService } from '../service/trade-challenge.service';
import { IMt4Account } from 'app/entities/mt-4-account/mt-4-account.model';
import { Mt4AccountService } from 'app/entities/mt-4-account/service/mt-4-account.service';
import { ISiteAccount } from 'app/entities/site-account/site-account.model';
import { SiteAccountService } from 'app/entities/site-account/service/site-account.service';
import { IChallengeType } from 'app/entities/challenge-type/challenge-type.model';
import { ChallengeTypeService } from 'app/entities/challenge-type/service/challenge-type.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-trade-challenge-update',
  templateUrl: './trade-challenge-update.component.html',
})
export class TradeChallengeUpdateComponent implements OnInit {
  isSaving = false;

  mt4AccountsCollection: IMt4Account[] = [];
  siteAccountsSharedCollection: ISiteAccount[] = [];
  challengeTypesSharedCollection: IChallengeType[] = [];
  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    tradeChallengeName: [],
    startDate: [],
    accountDayStartBalance: [],
    accountDayStartEquity: [],
    runningMaxTotalDrawdown: [],
    runningMaxDailyDrawdown: [],
    lowestDrawdownPoint: [],
    rulesViolated: [],
    ruleViolated: [],
    ruleViolatedDate: [],
    mt4Account: [],
    siteAccount: [],
    challengeType: [],
    user: [],
  });

  constructor(
    protected tradeChallengeService: TradeChallengeService,
    protected mt4AccountService: Mt4AccountService,
    protected siteAccountService: SiteAccountService,
    protected challengeTypeService: ChallengeTypeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradeChallenge }) => {
      if (tradeChallenge.id === undefined) {
        const today = dayjs().startOf('day');
        tradeChallenge.startDate = today;
        tradeChallenge.ruleViolatedDate = today;
      }

      this.updateForm(tradeChallenge);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tradeChallenge = this.createFromForm();
    if (tradeChallenge.id !== undefined) {
      this.subscribeToSaveResponse(this.tradeChallengeService.update(tradeChallenge));
    } else {
      this.subscribeToSaveResponse(this.tradeChallengeService.create(tradeChallenge));
    }
  }

  trackMt4AccountById(_index: number, item: IMt4Account): number {
    return item.id!;
  }

  trackSiteAccountById(_index: number, item: ISiteAccount): number {
    return item.id!;
  }

  trackChallengeTypeById(_index: number, item: IChallengeType): number {
    return item.id!;
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITradeChallenge>>): void {
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

  protected updateForm(tradeChallenge: ITradeChallenge): void {
    this.editForm.patchValue({
      id: tradeChallenge.id,
      tradeChallengeName: tradeChallenge.tradeChallengeName,
      startDate: tradeChallenge.startDate ? tradeChallenge.startDate.format(DATE_TIME_FORMAT) : null,
      accountDayStartBalance: tradeChallenge.accountDayStartBalance,
      accountDayStartEquity: tradeChallenge.accountDayStartEquity,
      runningMaxTotalDrawdown: tradeChallenge.runningMaxTotalDrawdown,
      runningMaxDailyDrawdown: tradeChallenge.runningMaxDailyDrawdown,
      lowestDrawdownPoint: tradeChallenge.lowestDrawdownPoint,
      rulesViolated: tradeChallenge.rulesViolated,
      ruleViolated: tradeChallenge.ruleViolated,
      ruleViolatedDate: tradeChallenge.ruleViolatedDate ? tradeChallenge.ruleViolatedDate.format(DATE_TIME_FORMAT) : null,
      mt4Account: tradeChallenge.mt4Account,
      siteAccount: tradeChallenge.siteAccount,
      challengeType: tradeChallenge.challengeType,
      user: tradeChallenge.user,
    });

    this.mt4AccountsCollection = this.mt4AccountService.addMt4AccountToCollectionIfMissing(
      this.mt4AccountsCollection,
      tradeChallenge.mt4Account
    );
    this.siteAccountsSharedCollection = this.siteAccountService.addSiteAccountToCollectionIfMissing(
      this.siteAccountsSharedCollection,
      tradeChallenge.siteAccount
    );
    this.challengeTypesSharedCollection = this.challengeTypeService.addChallengeTypeToCollectionIfMissing(
      this.challengeTypesSharedCollection,
      tradeChallenge.challengeType
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, tradeChallenge.user);
  }

  protected loadRelationshipsOptions(): void {
    this.mt4AccountService
      .query({ 'tradeChallengeId.specified': 'false' })
      .pipe(map((res: HttpResponse<IMt4Account[]>) => res.body ?? []))
      .pipe(
        map((mt4Accounts: IMt4Account[]) =>
          this.mt4AccountService.addMt4AccountToCollectionIfMissing(mt4Accounts, this.editForm.get('mt4Account')!.value)
        )
      )
      .subscribe((mt4Accounts: IMt4Account[]) => (this.mt4AccountsCollection = mt4Accounts));

    this.siteAccountService
      .query()
      .pipe(map((res: HttpResponse<ISiteAccount[]>) => res.body ?? []))
      .pipe(
        map((siteAccounts: ISiteAccount[]) =>
          this.siteAccountService.addSiteAccountToCollectionIfMissing(siteAccounts, this.editForm.get('siteAccount')!.value)
        )
      )
      .subscribe((siteAccounts: ISiteAccount[]) => (this.siteAccountsSharedCollection = siteAccounts));

    this.challengeTypeService
      .query()
      .pipe(map((res: HttpResponse<IChallengeType[]>) => res.body ?? []))
      .pipe(
        map((challengeTypes: IChallengeType[]) =>
          this.challengeTypeService.addChallengeTypeToCollectionIfMissing(challengeTypes, this.editForm.get('challengeType')!.value)
        )
      )
      .subscribe((challengeTypes: IChallengeType[]) => (this.challengeTypesSharedCollection = challengeTypes));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): ITradeChallenge {
    return {
      ...new TradeChallenge(),
      id: this.editForm.get(['id'])!.value,
      tradeChallengeName: this.editForm.get(['tradeChallengeName'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? dayjs(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      accountDayStartBalance: this.editForm.get(['accountDayStartBalance'])!.value,
      accountDayStartEquity: this.editForm.get(['accountDayStartEquity'])!.value,
      runningMaxTotalDrawdown: this.editForm.get(['runningMaxTotalDrawdown'])!.value,
      runningMaxDailyDrawdown: this.editForm.get(['runningMaxDailyDrawdown'])!.value,
      lowestDrawdownPoint: this.editForm.get(['lowestDrawdownPoint'])!.value,
      rulesViolated: this.editForm.get(['rulesViolated'])!.value,
      ruleViolated: this.editForm.get(['ruleViolated'])!.value,
      ruleViolatedDate: this.editForm.get(['ruleViolatedDate'])!.value
        ? dayjs(this.editForm.get(['ruleViolatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      mt4Account: this.editForm.get(['mt4Account'])!.value,
      siteAccount: this.editForm.get(['siteAccount'])!.value,
      challengeType: this.editForm.get(['challengeType'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
