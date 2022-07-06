import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TradersChallengeTrackerDetailComponent } from './traders-challenge-tracker-detail.component';

describe('TradersChallengeTracker Management Detail Component', () => {
  let comp: TradersChallengeTrackerDetailComponent;
  let fixture: ComponentFixture<TradersChallengeTrackerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TradersChallengeTrackerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tradersChallengeTracker: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TradersChallengeTrackerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TradersChallengeTrackerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tradersChallengeTracker on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tradersChallengeTracker).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
