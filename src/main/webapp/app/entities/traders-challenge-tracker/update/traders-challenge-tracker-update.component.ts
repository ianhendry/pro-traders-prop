import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITradersChallengeTracker, TradersChallengeTracker } from '../traders-challenge-tracker.model';
import { TradersChallengeTrackerService } from '../service/traders-challenge-tracker.service';
import { IMt4Account } from 'app/entities/mt-4-account/mt-4-account.model';
import { Mt4AccountService } from 'app/entities/mt-4-account/service/mt-4-account.service';
import { IChallengeType } from 'app/entities/challenge-type/challenge-type.model';
import { ChallengeTypeService } from 'app/entities/challenge-type/service/challenge-type.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-traders-challenge-tracker-update',
  templateUrl: './traders-challenge-tracker-update.component.html',
})
export class TradersChallengeTrackerUpdateComponent implements OnInit {
  isSaving = false;

  mt4AccountsCollection: IMt4Account[] = [];
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
    challengeType: [],
    user: [],
  });

  constructor(
    protected tradersChallengeTrackerService: TradersChallengeTrackerService,
    protected mt4AccountService: Mt4AccountService,
    protected challengeTypeService: ChallengeTypeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradersChallengeTracker }) => {
      if (tradersChallengeTracker.id === undefined) {
        const today = dayjs().startOf('day');
        tradersChallengeTracker.startDate = today;
        tradersChallengeTracker.ruleViolatedDate = today;
      }

      this.updateForm(tradersChallengeTracker);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tradersChallengeTracker = this.createFromForm();
    if (tradersChallengeTracker.id !== undefined) {
      this.subscribeToSaveResponse(this.tradersChallengeTrackerService.update(tradersChallengeTracker));
    } else {
      this.subscribeToSaveResponse(this.tradersChallengeTrackerService.create(tradersChallengeTracker));
    }
  }

  trackMt4AccountById(_index: number, item: IMt4Account): number {
    return item.id!;
  }

  trackChallengeTypeById(_index: number, item: IChallengeType): number {
    return item.id!;
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITradersChallengeTracker>>): void {
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

  protected updateForm(tradersChallengeTracker: ITradersChallengeTracker): void {
    this.editForm.patchValue({
      id: tradersChallengeTracker.id,
      tradeChallengeName: tradersChallengeTracker.tradeChallengeName,
      startDate: tradersChallengeTracker.startDate ? tradersChallengeTracker.startDate.format(DATE_TIME_FORMAT) : null,
      accountDayStartBalance: tradersChallengeTracker.accountDayStartBalance,
      accountDayStartEquity: tradersChallengeTracker.accountDayStartEquity,
      runningMaxTotalDrawdown: tradersChallengeTracker.runningMaxTotalDrawdown,
      runningMaxDailyDrawdown: tradersChallengeTracker.runningMaxDailyDrawdown,
      lowestDrawdownPoint: tradersChallengeTracker.lowestDrawdownPoint,
      rulesViolated: tradersChallengeTracker.rulesViolated,
      ruleViolated: tradersChallengeTracker.ruleViolated,
      ruleViolatedDate: tradersChallengeTracker.ruleViolatedDate ? tradersChallengeTracker.ruleViolatedDate.format(DATE_TIME_FORMAT) : null,
      mt4Account: tradersChallengeTracker.mt4Account,
      challengeType: tradersChallengeTracker.challengeType,
      user: tradersChallengeTracker.user,
    });

    this.mt4AccountsCollection = this.mt4AccountService.addMt4AccountToCollectionIfMissing(
      this.mt4AccountsCollection,
      tradersChallengeTracker.mt4Account
    );
    this.challengeTypesSharedCollection = this.challengeTypeService.addChallengeTypeToCollectionIfMissing(
      this.challengeTypesSharedCollection,
      tradersChallengeTracker.challengeType
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, tradersChallengeTracker.user);
  }

  protected loadRelationshipsOptions(): void {
    this.mt4AccountService
      .query({ 'tradersChallengeTrackerId.specified': 'false' })
      .pipe(map((res: HttpResponse<IMt4Account[]>) => res.body ?? []))
      .pipe(
        map((mt4Accounts: IMt4Account[]) =>
          this.mt4AccountService.addMt4AccountToCollectionIfMissing(mt4Accounts, this.editForm.get('mt4Account')!.value)
        )
      )
      .subscribe((mt4Accounts: IMt4Account[]) => (this.mt4AccountsCollection = mt4Accounts));

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

  protected createFromForm(): ITradersChallengeTracker {
    return {
      ...new TradersChallengeTracker(),
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
      challengeType: this.editForm.get(['challengeType'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
