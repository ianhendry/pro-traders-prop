import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITradersChallengeTracker, getTradersChallengeTrackerIdentifier } from '../traders-challenge-tracker.model';

export type EntityResponseType = HttpResponse<ITradersChallengeTracker>;
export type EntityArrayResponseType = HttpResponse<ITradersChallengeTracker[]>;

@Injectable({ providedIn: 'root' })
export class TradersChallengeTrackerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/traders-challenge-trackers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tradersChallengeTracker: ITradersChallengeTracker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tradersChallengeTracker);
    return this.http
      .post<ITradersChallengeTracker>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tradersChallengeTracker: ITradersChallengeTracker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tradersChallengeTracker);
    return this.http
      .put<ITradersChallengeTracker>(
        `${this.resourceUrl}/${getTradersChallengeTrackerIdentifier(tradersChallengeTracker) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tradersChallengeTracker: ITradersChallengeTracker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tradersChallengeTracker);
    return this.http
      .patch<ITradersChallengeTracker>(
        `${this.resourceUrl}/${getTradersChallengeTrackerIdentifier(tradersChallengeTracker) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITradersChallengeTracker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITradersChallengeTracker[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTradersChallengeTrackerToCollectionIfMissing(
    tradersChallengeTrackerCollection: ITradersChallengeTracker[],
    ...tradersChallengeTrackersToCheck: (ITradersChallengeTracker | null | undefined)[]
  ): ITradersChallengeTracker[] {
    const tradersChallengeTrackers: ITradersChallengeTracker[] = tradersChallengeTrackersToCheck.filter(isPresent);
    if (tradersChallengeTrackers.length > 0) {
      const tradersChallengeTrackerCollectionIdentifiers = tradersChallengeTrackerCollection.map(
        tradersChallengeTrackerItem => getTradersChallengeTrackerIdentifier(tradersChallengeTrackerItem)!
      );
      const tradersChallengeTrackersToAdd = tradersChallengeTrackers.filter(tradersChallengeTrackerItem => {
        const tradersChallengeTrackerIdentifier = getTradersChallengeTrackerIdentifier(tradersChallengeTrackerItem);
        if (
          tradersChallengeTrackerIdentifier == null ||
          tradersChallengeTrackerCollectionIdentifiers.includes(tradersChallengeTrackerIdentifier)
        ) {
          return false;
        }
        tradersChallengeTrackerCollectionIdentifiers.push(tradersChallengeTrackerIdentifier);
        return true;
      });
      return [...tradersChallengeTrackersToAdd, ...tradersChallengeTrackerCollection];
    }
    return tradersChallengeTrackerCollection;
  }

  protected convertDateFromClient(tradersChallengeTracker: ITradersChallengeTracker): ITradersChallengeTracker {
    return Object.assign({}, tradersChallengeTracker, {
      startDate: tradersChallengeTracker.startDate?.isValid() ? tradersChallengeTracker.startDate.toJSON() : undefined,
      ruleViolatedDate: tradersChallengeTracker.ruleViolatedDate?.isValid() ? tradersChallengeTracker.ruleViolatedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
      res.body.ruleViolatedDate = res.body.ruleViolatedDate ? dayjs(res.body.ruleViolatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tradersChallengeTracker: ITradersChallengeTracker) => {
        tradersChallengeTracker.startDate = tradersChallengeTracker.startDate ? dayjs(tradersChallengeTracker.startDate) : undefined;
        tradersChallengeTracker.ruleViolatedDate = tradersChallengeTracker.ruleViolatedDate
          ? dayjs(tradersChallengeTracker.ruleViolatedDate)
          : undefined;
      });
    }
    return res;
  }
}
