import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SiteAccountService } from '../service/site-account.service';
import { ISiteAccount, SiteAccount } from '../site-account.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IBillingDetails } from 'app/entities/billing-details/billing-details.model';
import { BillingDetailsService } from 'app/entities/billing-details/service/billing-details.service';

import { SiteAccountUpdateComponent } from './site-account-update.component';

describe('SiteAccount Management Update Component', () => {
  let comp: SiteAccountUpdateComponent;
  let fixture: ComponentFixture<SiteAccountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let siteAccountService: SiteAccountService;
  let userService: UserService;
  let billingDetailsService: BillingDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SiteAccountUpdateComponent],
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
      .overrideTemplate(SiteAccountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SiteAccountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    siteAccountService = TestBed.inject(SiteAccountService);
    userService = TestBed.inject(UserService);
    billingDetailsService = TestBed.inject(BillingDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const siteAccount: ISiteAccount = { id: 456 };
      const user: IUser = { id: 95529 };
      siteAccount.user = user;
      const user: IUser = { id: 4807 };
      siteAccount.user = user;

      const userCollection: IUser[] = [{ id: 63353 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user, user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteAccount });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BillingDetails query and add missing value', () => {
      const siteAccount: ISiteAccount = { id: 456 };
      const addressDetails: IBillingDetails = { id: 90980 };
      siteAccount.addressDetails = addressDetails;

      const billingDetailsCollection: IBillingDetails[] = [{ id: 9137 }];
      jest.spyOn(billingDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: billingDetailsCollection })));
      const additionalBillingDetails = [addressDetails];
      const expectedCollection: IBillingDetails[] = [...additionalBillingDetails, ...billingDetailsCollection];
      jest.spyOn(billingDetailsService, 'addBillingDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ siteAccount });
      comp.ngOnInit();

      expect(billingDetailsService.query).toHaveBeenCalled();
      expect(billingDetailsService.addBillingDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        billingDetailsCollection,
        ...additionalBillingDetails
      );
      expect(comp.billingDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const siteAccount: ISiteAccount = { id: 456 };
      const user: IUser = { id: 55657 };
      siteAccount.user = user;
      const user: IUser = { id: 99380 };
      siteAccount.user = user;
      const addressDetails: IBillingDetails = { id: 41668 };
      siteAccount.addressDetails = addressDetails;

      activatedRoute.data = of({ siteAccount });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(siteAccount));
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.billingDetailsSharedCollection).toContain(addressDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteAccount>>();
      const siteAccount = { id: 123 };
      jest.spyOn(siteAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteAccount }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(siteAccountService.update).toHaveBeenCalledWith(siteAccount);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteAccount>>();
      const siteAccount = new SiteAccount();
      jest.spyOn(siteAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteAccount }));
      saveSubject.complete();

      // THEN
      expect(siteAccountService.create).toHaveBeenCalledWith(siteAccount);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SiteAccount>>();
      const siteAccount = { id: 123 };
      jest.spyOn(siteAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(siteAccountService.update).toHaveBeenCalledWith(siteAccount);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackBillingDetailsById', () => {
      it('Should return tracked BillingDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBillingDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
