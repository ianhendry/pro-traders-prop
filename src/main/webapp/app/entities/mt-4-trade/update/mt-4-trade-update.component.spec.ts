import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { Mt4TradeService } from '../service/mt-4-trade.service';
import { IMt4Trade, Mt4Trade } from '../mt-4-trade.model';
import { IMt4Account } from 'app/entities/mt-4-account/mt-4-account.model';
import { Mt4AccountService } from 'app/entities/mt-4-account/service/mt-4-account.service';

import { Mt4TradeUpdateComponent } from './mt-4-trade-update.component';

describe('Mt4Trade Management Update Component', () => {
  let comp: Mt4TradeUpdateComponent;
  let fixture: ComponentFixture<Mt4TradeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mt4TradeService: Mt4TradeService;
  let mt4AccountService: Mt4AccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [Mt4TradeUpdateComponent],
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
      .overrideTemplate(Mt4TradeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(Mt4TradeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mt4TradeService = TestBed.inject(Mt4TradeService);
    mt4AccountService = TestBed.inject(Mt4AccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Mt4Account query and add missing value', () => {
      const mt4Trade: IMt4Trade = { id: 456 };
      const mt4Account: IMt4Account = { id: 68098 };
      mt4Trade.mt4Account = mt4Account;

      const mt4AccountCollection: IMt4Account[] = [{ id: 92203 }];
      jest.spyOn(mt4AccountService, 'query').mockReturnValue(of(new HttpResponse({ body: mt4AccountCollection })));
      const additionalMt4Accounts = [mt4Account];
      const expectedCollection: IMt4Account[] = [...additionalMt4Accounts, ...mt4AccountCollection];
      jest.spyOn(mt4AccountService, 'addMt4AccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mt4Trade });
      comp.ngOnInit();

      expect(mt4AccountService.query).toHaveBeenCalled();
      expect(mt4AccountService.addMt4AccountToCollectionIfMissing).toHaveBeenCalledWith(mt4AccountCollection, ...additionalMt4Accounts);
      expect(comp.mt4AccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mt4Trade: IMt4Trade = { id: 456 };
      const mt4Account: IMt4Account = { id: 42543 };
      mt4Trade.mt4Account = mt4Account;

      activatedRoute.data = of({ mt4Trade });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mt4Trade));
      expect(comp.mt4AccountsSharedCollection).toContain(mt4Account);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Mt4Trade>>();
      const mt4Trade = { id: 123 };
      jest.spyOn(mt4TradeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mt4Trade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mt4Trade }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mt4TradeService.update).toHaveBeenCalledWith(mt4Trade);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Mt4Trade>>();
      const mt4Trade = new Mt4Trade();
      jest.spyOn(mt4TradeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mt4Trade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mt4Trade }));
      saveSubject.complete();

      // THEN
      expect(mt4TradeService.create).toHaveBeenCalledWith(mt4Trade);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Mt4Trade>>();
      const mt4Trade = { id: 123 };
      jest.spyOn(mt4TradeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mt4Trade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mt4TradeService.update).toHaveBeenCalledWith(mt4Trade);
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
  });
});
