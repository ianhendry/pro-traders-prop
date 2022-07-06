import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BillingDetailsService } from '../service/billing-details.service';
import { IBillingDetails, BillingDetails } from '../billing-details.model';

import { BillingDetailsUpdateComponent } from './billing-details-update.component';

describe('BillingDetails Management Update Component', () => {
  let comp: BillingDetailsUpdateComponent;
  let fixture: ComponentFixture<BillingDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let billingDetailsService: BillingDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BillingDetailsUpdateComponent],
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
      .overrideTemplate(BillingDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BillingDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    billingDetailsService = TestBed.inject(BillingDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const billingDetails: IBillingDetails = { id: 456 };

      activatedRoute.data = of({ billingDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(billingDetails));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BillingDetails>>();
      const billingDetails = { id: 123 };
      jest.spyOn(billingDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ billingDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: billingDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(billingDetailsService.update).toHaveBeenCalledWith(billingDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BillingDetails>>();
      const billingDetails = new BillingDetails();
      jest.spyOn(billingDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ billingDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: billingDetails }));
      saveSubject.complete();

      // THEN
      expect(billingDetailsService.create).toHaveBeenCalledWith(billingDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BillingDetails>>();
      const billingDetails = { id: 123 };
      jest.spyOn(billingDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ billingDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(billingDetailsService.update).toHaveBeenCalledWith(billingDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
