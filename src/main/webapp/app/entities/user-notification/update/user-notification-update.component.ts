import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserNotification, UserNotification } from '../user-notification.model';
import { UserNotificationService } from '../service/user-notification.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ITradersChallengeTracker } from 'app/entities/traders-challenge-tracker/traders-challenge-tracker.model';
import { TradersChallengeTrackerService } from 'app/entities/traders-challenge-tracker/service/traders-challenge-tracker.service';

@Component({
  selector: 'jhi-user-notification-update',
  templateUrl: './user-notification-update.component.html',
})
export class UserNotificationUpdateComponent implements OnInit {
  isSaving = false;

  tradersChallengeTrackersSharedCollection: ITradersChallengeTracker[] = [];

  editForm = this.fb.group({
    id: [],
    commentTitle: [null, [Validators.required]],
    commentBody: [],
    commentMedia: [],
    commentMediaContentType: [],
    dateAdded: [null, [Validators.required]],
    makePublicVisibleOnSite: [],
    tradersChallengeTracker: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected userNotificationService: UserNotificationService,
    protected tradersChallengeTrackerService: TradersChallengeTrackerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userNotification }) => {
      if (userNotification.id === undefined) {
        const today = dayjs().startOf('day');
        userNotification.dateAdded = today;
      }

      this.updateForm(userNotification);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('proTradersPropApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userNotification = this.createFromForm();
    if (userNotification.id !== undefined) {
      this.subscribeToSaveResponse(this.userNotificationService.update(userNotification));
    } else {
      this.subscribeToSaveResponse(this.userNotificationService.create(userNotification));
    }
  }

  trackTradersChallengeTrackerById(_index: number, item: ITradersChallengeTracker): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserNotification>>): void {
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

  protected updateForm(userNotification: IUserNotification): void {
    this.editForm.patchValue({
      id: userNotification.id,
      commentTitle: userNotification.commentTitle,
      commentBody: userNotification.commentBody,
      commentMedia: userNotification.commentMedia,
      commentMediaContentType: userNotification.commentMediaContentType,
      dateAdded: userNotification.dateAdded ? userNotification.dateAdded.format(DATE_TIME_FORMAT) : null,
      makePublicVisibleOnSite: userNotification.makePublicVisibleOnSite,
      tradersChallengeTracker: userNotification.tradersChallengeTracker,
    });

    this.tradersChallengeTrackersSharedCollection = this.tradersChallengeTrackerService.addTradersChallengeTrackerToCollectionIfMissing(
      this.tradersChallengeTrackersSharedCollection,
      userNotification.tradersChallengeTracker
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tradersChallengeTrackerService
      .query()
      .pipe(map((res: HttpResponse<ITradersChallengeTracker[]>) => res.body ?? []))
      .pipe(
        map((tradersChallengeTrackers: ITradersChallengeTracker[]) =>
          this.tradersChallengeTrackerService.addTradersChallengeTrackerToCollectionIfMissing(
            tradersChallengeTrackers,
            this.editForm.get('tradersChallengeTracker')!.value
          )
        )
      )
      .subscribe(
        (tradersChallengeTrackers: ITradersChallengeTracker[]) => (this.tradersChallengeTrackersSharedCollection = tradersChallengeTrackers)
      );
  }

  protected createFromForm(): IUserNotification {
    return {
      ...new UserNotification(),
      id: this.editForm.get(['id'])!.value,
      commentTitle: this.editForm.get(['commentTitle'])!.value,
      commentBody: this.editForm.get(['commentBody'])!.value,
      commentMediaContentType: this.editForm.get(['commentMediaContentType'])!.value,
      commentMedia: this.editForm.get(['commentMedia'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value ? dayjs(this.editForm.get(['dateAdded'])!.value, DATE_TIME_FORMAT) : undefined,
      makePublicVisibleOnSite: this.editForm.get(['makePublicVisibleOnSite'])!.value,
      tradersChallengeTracker: this.editForm.get(['tradersChallengeTracker'])!.value,
    };
  }
}
