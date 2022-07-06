import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChallengeTypeService } from '../service/challenge-type.service';
import { IChallengeType, ChallengeType } from '../challenge-type.model';
import { ISiteAccount } from 'app/entities/site-account/site-account.model';
import { SiteAccountService } from 'app/entities/site-account/service/site-account.service';

import { ChallengeTypeUpdateComponent } from './challenge-type-update.component';

describe('ChallengeType Management Update Component', () => {
  let comp: ChallengeTypeUpdateComponent;
  let fixture: ComponentFixture<ChallengeTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let challengeTypeService: ChallengeTypeService;
  let siteAccountService: SiteAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChallengeTypeUpdateComponent],
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
      .overrideTemplate(ChallengeTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChallengeTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    challengeTypeService = TestBed.inject(ChallengeTypeService);
    siteAccountService = TestBed.inject(SiteAccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SiteAccount query and add missing value', () => {
      const challengeType: IChallengeType = { id: 456 };
      const siteAccount: ISiteAccount = { id: 10718 };
      challengeType.siteAccount = siteAccount;

      const siteAccountCollection: ISiteAccount[] = [{ id: 28233 }];
      jest.spyOn(siteAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: siteAccountCollection })));
      const additionalSiteAccounts = [siteAccount];
      const expectedCollection: ISiteAccount[] = [...additionalSiteAccounts, ...siteAccountCollection];
      jest.spyOn(siteAccountService, 'addSiteAccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ challengeType });
      comp.ngOnInit();

      expect(siteAccountService.query).toHaveBeenCalled();
      expect(siteAccountService.addSiteAccountToCollectionIfMissing).toHaveBeenCalledWith(siteAccountCollection, ...additionalSiteAccounts);
      expect(comp.siteAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const challengeType: IChallengeType = { id: 456 };
      const siteAccount: ISiteAccount = { id: 90316 };
      challengeType.siteAccount = siteAccount;

      activatedRoute.data = of({ challengeType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(challengeType));
      expect(comp.siteAccountsSharedCollection).toContain(siteAccount);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChallengeType>>();
      const challengeType = { id: 123 };
      jest.spyOn(challengeTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ challengeType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: challengeType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(challengeTypeService.update).toHaveBeenCalledWith(challengeType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChallengeType>>();
      const challengeType = new ChallengeType();
      jest.spyOn(challengeTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ challengeType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: challengeType }));
      saveSubject.complete();

      // THEN
      expect(challengeTypeService.create).toHaveBeenCalledWith(challengeType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChallengeType>>();
      const challengeType = { id: 123 };
      jest.spyOn(challengeTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ challengeType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(challengeTypeService.update).toHaveBeenCalledWith(challengeType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSiteAccountById', () => {
      it('Should return tracked SiteAccount primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSiteAccountById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
