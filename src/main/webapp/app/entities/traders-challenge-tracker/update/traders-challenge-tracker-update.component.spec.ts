import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TradersChallengeTrackerService } from '../service/traders-challenge-tracker.service';
import { ITradersChallengeTracker, TradersChallengeTracker } from '../traders-challenge-tracker.model';
import { IMt4Account } from 'app/entities/mt-4-account/mt-4-account.model';
import { Mt4AccountService } from 'app/entities/mt-4-account/service/mt-4-account.service';
import { IChallengeType } from 'app/entities/challenge-type/challenge-type.model';
import { ChallengeTypeService } from 'app/entities/challenge-type/service/challenge-type.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { TradersChallengeTrackerUpdateComponent } from './traders-challenge-tracker-update.component';

describe('TradersChallengeTracker Management Update Component', () => {
  let comp: TradersChallengeTrackerUpdateComponent;
  let fixture: ComponentFixture<TradersChallengeTrackerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tradersChallengeTrackerService: TradersChallengeTrackerService;
  let mt4AccountService: Mt4AccountService;
  let challengeTypeService: ChallengeTypeService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TradersChallengeTrackerUpdateComponent],
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
      .overrideTemplate(TradersChallengeTrackerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TradersChallengeTrackerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tradersChallengeTrackerService = TestBed.inject(TradersChallengeTrackerService);
    mt4AccountService = TestBed.inject(Mt4AccountService);
    challengeTypeService = TestBed.inject(ChallengeTypeService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call mt4Account query and add missing value', () => {
      const tradersChallengeTracker: ITradersChallengeTracker = { id: 456 };
      const mt4Account: IMt4Account = { id: 49640 };
      tradersChallengeTracker.mt4Account = mt4Account;

      const mt4AccountCollection: IMt4Account[] = [{ id: 88838 }];
      jest.spyOn(mt4AccountService, 'query').mockReturnValue(of(new HttpResponse({ body: mt4AccountCollection })));
      const expectedCollection: IMt4Account[] = [mt4Account, ...mt4AccountCollection];
      jest.spyOn(mt4AccountService, 'addMt4AccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tradersChallengeTracker });
      comp.ngOnInit();

      expect(mt4AccountService.query).toHaveBeenCalled();
      expect(mt4AccountService.addMt4AccountToCollectionIfMissing).toHaveBeenCalledWith(mt4AccountCollection, mt4Account);
      expect(comp.mt4AccountsCollection).toEqual(expectedCollection);
    });

    it('Should call ChallengeType query and add missing value', () => {
      const tradersChallengeTracker: ITradersChallengeTracker = { id: 456 };
      const challengeType: IChallengeType = { id: 19590 };
      tradersChallengeTracker.challengeType = challengeType;

      const challengeTypeCollection: IChallengeType[] = [{ id: 28570 }];
      jest.spyOn(challengeTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: challengeTypeCollection })));
      const additionalChallengeTypes = [challengeType];
      const expectedCollection: IChallengeType[] = [...additionalChallengeTypes, ...challengeTypeCollection];
      jest.spyOn(challengeTypeService, 'addChallengeTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tradersChallengeTracker });
      comp.ngOnInit();

      expect(challengeTypeService.query).toHaveBeenCalled();
      expect(challengeTypeService.addChallengeTypeToCollectionIfMissing).toHaveBeenCalledWith(
        challengeTypeCollection,
        ...additionalChallengeTypes
      );
      expect(comp.challengeTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const tradersChallengeTracker: ITradersChallengeTracker = { id: 456 };
      const user: IUser = { id: 69084 };
      tradersChallengeTracker.user = user;

      const userCollection: IUser[] = [{ id: 38863 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tradersChallengeTracker });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tradersChallengeTracker: ITradersChallengeTracker = { id: 456 };
      const mt4Account: IMt4Account = { id: 33851 };
      tradersChallengeTracker.mt4Account = mt4Account;
      const challengeType: IChallengeType = { id: 97190 };
      tradersChallengeTracker.challengeType = challengeType;
      const user: IUser = { id: 40400 };
      tradersChallengeTracker.user = user;

      activatedRoute.data = of({ tradersChallengeTracker });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tradersChallengeTracker));
      expect(comp.mt4AccountsCollection).toContain(mt4Account);
      expect(comp.challengeTypesSharedCollection).toContain(challengeType);
      expect(comp.usersSharedCollection).toContain(user);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TradersChallengeTracker>>();
      const tradersChallengeTracker = { id: 123 };
      jest.spyOn(tradersChallengeTrackerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tradersChallengeTracker });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tradersChallengeTracker }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tradersChallengeTrackerService.update).toHaveBeenCalledWith(tradersChallengeTracker);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TradersChallengeTracker>>();
      const tradersChallengeTracker = new TradersChallengeTracker();
      jest.spyOn(tradersChallengeTrackerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tradersChallengeTracker });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tradersChallengeTracker }));
      saveSubject.complete();

      // THEN
      expect(tradersChallengeTrackerService.create).toHaveBeenCalledWith(tradersChallengeTracker);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TradersChallengeTracker>>();
      const tradersChallengeTracker = { id: 123 };
      jest.spyOn(tradersChallengeTrackerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tradersChallengeTracker });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tradersChallengeTrackerService.update).toHaveBeenCalledWith(tradersChallengeTracker);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMt4AccountById', () => {
      it('Should return tracked Mt4Account primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMt4AccountById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackChallengeTypeById', () => {
      it('Should return tracked ChallengeType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackChallengeTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
