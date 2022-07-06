import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserNotificationService } from '../service/user-notification.service';
import { IUserNotification, UserNotification } from '../user-notification.model';
import { ITradersChallengeTracker } from 'app/entities/traders-challenge-tracker/traders-challenge-tracker.model';
import { TradersChallengeTrackerService } from 'app/entities/traders-challenge-tracker/service/traders-challenge-tracker.service';

import { UserNotificationUpdateComponent } from './user-notification-update.component';

describe('UserNotification Management Update Component', () => {
  let comp: UserNotificationUpdateComponent;
  let fixture: ComponentFixture<UserNotificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userNotificationService: UserNotificationService;
  let tradersChallengeTrackerService: TradersChallengeTrackerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserNotificationUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(UserNotificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserNotificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userNotificationService = TestBed.inject(UserNotificationService);
    tradersChallengeTrackerService = TestBed.inject(TradersChallengeTrackerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TradersChallengeTracker query and add missing value', () => {
      const userNotification: IUserNotification = { id: 456 };
      const tradersChallengeTracker: ITradersChallengeTracker = { id: 99737 };
      userNotification.tradersChallengeTracker = tradersChallengeTracker;

      const tradersChallengeTrackerCollection: ITradersChallengeTracker[] = [{ id: 27813 }];
      jest
        .spyOn(tradersChallengeTrackerService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: tradersChallengeTrackerCollection })));
      const additionalTradersChallengeTrackers = [tradersChallengeTracker];
      const expectedCollection: ITradersChallengeTracker[] = [...additionalTradersChallengeTrackers, ...tradersChallengeTrackerCollection];
      jest.spyOn(tradersChallengeTrackerService, 'addTradersChallengeTrackerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userNotification });
      comp.ngOnInit();

      expect(tradersChallengeTrackerService.query).toHaveBeenCalled();
      expect(tradersChallengeTrackerService.addTradersChallengeTrackerToCollectionIfMissing).toHaveBeenCalledWith(
        tradersChallengeTrackerCollection,
        ...additionalTradersChallengeTrackers
      );
      expect(comp.tradersChallengeTrackersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userNotification: IUserNotification = { id: 456 };
      const tradersChallengeTracker: ITradersChallengeTracker = { id: 51568 };
      userNotification.tradersChallengeTracker = tradersChallengeTracker;

      activatedRoute.data = of({ userNotification });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(userNotification));
      expect(comp.tradersChallengeTrackersSharedCollection).toContain(tradersChallengeTracker);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserNotification>>();
      const userNotification = { id: 123 };
      jest.spyOn(userNotificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userNotification }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(userNotificationService.update).toHaveBeenCalledWith(userNotification);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserNotification>>();
      const userNotification = new UserNotification();
      jest.spyOn(userNotificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userNotification }));
      saveSubject.complete();

      // THEN
      expect(userNotificationService.create).toHaveBeenCalledWith(userNotification);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UserNotification>>();
      const userNotification = { id: 123 };
      jest.spyOn(userNotificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userNotificationService.update).toHaveBeenCalledWith(userNotification);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTradersChallengeTrackerById', () => {
      it('Should return tracked TradersChallengeTracker primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTradersChallengeTrackerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
