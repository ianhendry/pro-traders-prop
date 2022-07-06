import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITradersChallengeTracker } from '../traders-challenge-tracker.model';

@Component({
  selector: 'jhi-traders-challenge-tracker-detail',
  templateUrl: './traders-challenge-tracker-detail.component.html',
})
export class TradersChallengeTrackerDetailComponent implements OnInit {
  tradersChallengeTracker: ITradersChallengeTracker | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradersChallengeTracker }) => {
      this.tradersChallengeTracker = tradersChallengeTracker;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
