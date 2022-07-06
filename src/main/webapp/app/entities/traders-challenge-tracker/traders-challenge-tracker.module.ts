import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TradersChallengeTrackerComponent } from './list/traders-challenge-tracker.component';
import { TradersChallengeTrackerDetailComponent } from './detail/traders-challenge-tracker-detail.component';
import { TradersChallengeTrackerUpdateComponent } from './update/traders-challenge-tracker-update.component';
import { TradersChallengeTrackerDeleteDialogComponent } from './delete/traders-challenge-tracker-delete-dialog.component';
import { TradersChallengeTrackerRoutingModule } from './route/traders-challenge-tracker-routing.module';

@NgModule({
  imports: [SharedModule, TradersChallengeTrackerRoutingModule],
  declarations: [
    TradersChallengeTrackerComponent,
    TradersChallengeTrackerDetailComponent,
    TradersChallengeTrackerUpdateComponent,
    TradersChallengeTrackerDeleteDialogComponent,
  ],
  entryComponents: [TradersChallengeTrackerDeleteDialogComponent],
})
export class TradersChallengeTrackerModule {}
