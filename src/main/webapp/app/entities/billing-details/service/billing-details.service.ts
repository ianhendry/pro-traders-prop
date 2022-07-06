import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBillingDetails, getBillingDetailsIdentifier } from '../billing-details.model';

export type EntityResponseType = HttpResponse<IBillingDetails>;
export type EntityArrayResponseType = HttpResponse<IBillingDetails[]>;

@Injectable({ providedIn: 'root' })
export class BillingDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/billing-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(billingDetails: IBillingDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billingDetails);
    return this.http
      .post<IBillingDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(billingDetails: IBillingDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billingDetails);
    return this.http
      .put<IBillingDetails>(`${this.resourceUrl}/${getBillingDetailsIdentifier(billingDetails) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(billingDetails: IBillingDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(billingDetails);
    return this.http
      .patch<IBillingDetails>(`${this.resourceUrl}/${getBillingDetailsIdentifier(billingDetails) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBillingDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBillingDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBillingDetailsToCollectionIfMissing(
    billingDetailsCollection: IBillingDetails[],
    ...billingDetailsToCheck: (IBillingDetails | null | undefined)[]
  ): IBillingDetails[] {
    const billingDetails: IBillingDetails[] = billingDetailsToCheck.filter(isPresent);
    if (billingDetails.length > 0) {
      const billingDetailsCollectionIdentifiers = billingDetailsCollection.map(
        billingDetailsItem => getBillingDetailsIdentifier(billingDetailsItem)!
      );
      const billingDetailsToAdd = billingDetails.filter(billingDetailsItem => {
        const billingDetailsIdentifier = getBillingDetailsIdentifier(billingDetailsItem);
        if (billingDetailsIdentifier == null || billingDetailsCollectionIdentifiers.includes(billingDetailsIdentifier)) {
          return false;
        }
        billingDetailsCollectionIdentifiers.push(billingDetailsIdentifier);
        return true;
      });
      return [...billingDetailsToAdd, ...billingDetailsCollection];
    }
    return billingDetailsCollection;
  }

  protected convertDateFromClient(billingDetails: IBillingDetails): IBillingDetails {
    return Object.assign({}, billingDetails, {
      dateAdded: billingDetails.dateAdded?.isValid() ? billingDetails.dateAdded.toJSON() : undefined,
      inActiveDate: billingDetails.inActiveDate?.isValid() ? billingDetails.inActiveDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAdded = res.body.dateAdded ? dayjs(res.body.dateAdded) : undefined;
      res.body.inActiveDate = res.body.inActiveDate ? dayjs(res.body.inActiveDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((billingDetails: IBillingDetails) => {
        billingDetails.dateAdded = billingDetails.dateAdded ? dayjs(billingDetails.dateAdded) : undefined;
        billingDetails.inActiveDate = billingDetails.inActiveDate ? dayjs(billingDetails.inActiveDate) : undefined;
      });
    }
    return res;
  }
}
