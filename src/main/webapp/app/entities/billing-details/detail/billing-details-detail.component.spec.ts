import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BillingDetailsDetailComponent } from './billing-details-detail.component';

describe('BillingDetails Management Detail Component', () => {
  let comp: BillingDetailsDetailComponent;
  let fixture: ComponentFixture<BillingDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BillingDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ billingDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BillingDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BillingDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load billingDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.billingDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
