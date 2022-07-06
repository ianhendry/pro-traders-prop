import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITradersChallengeTracker } from '../traders-challenge-tracker.model';
import { TradersChallengeTrackerService } from '../service/traders-challenge-tracker.service';

@Component({
  templateUrl: './traders-challenge-tracker-delete-dialog.component.html',
})
export class TradersChallengeTrackerDeleteDialogComponent {
  tradersChallengeTracker?: ITradersChallengeTracker;

  constructor(protected tradersChallengeTrackerService: TradersChallengeTrackerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tradersChallengeTrackerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
