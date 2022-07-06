import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BillingDetailsService } from '../service/billing-details.service';

import { BillingDetailsComponent } from './billing-details.component';

describe('BillingDetails Management Component', () => {
  let comp: BillingDetailsComponent;
  let fixture: ComponentFixture<BillingDetailsComponent>;
  let service: BillingDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BillingDetailsComponent],
    })
      .overrideTemplate(BillingDetailsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BillingDetailsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(BillingDetailsService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.billingDetails?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
