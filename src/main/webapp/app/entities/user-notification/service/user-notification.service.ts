import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserNotification, getUserNotificationIdentifier } from '../user-notification.model';

export type EntityResponseType = HttpResponse<IUserNotification>;
export type EntityArrayResponseType = HttpResponse<IUserNotification[]>;

@Injectable({ providedIn: 'root' })
export class UserNotificationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-notifications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userNotification: IUserNotification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userNotification);
    return this.http
      .post<IUserNotification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userNotification: IUserNotification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userNotification);
    return this.http
      .put<IUserNotification>(`${this.resourceUrl}/${getUserNotificationIdentifier(userNotification) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(userNotification: IUserNotification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userNotification);
    return this.http
      .patch<IUserNotification>(`${this.resourceUrl}/${getUserNotificationIdentifier(userNotification) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserNotification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserNotification[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUserNotificationToCollectionIfMissing(
    userNotificationCollection: IUserNotification[],
    ...userNotificationsToCheck: (IUserNotification | null | undefined)[]
  ): IUserNotification[] {
    const userNotifications: IUserNotification[] = userNotificationsToCheck.filter(isPresent);
    if (userNotifications.length > 0) {
      const userNotificationCollectionIdentifiers = userNotificationCollection.map(
        userNotificationItem => getUserNotificationIdentifier(userNotificationItem)!
      );
      const userNotificationsToAdd = userNotifications.filter(userNotificationItem => {
        const userNotificationIdentifier = getUserNotificationIdentifier(userNotificationItem);
        if (userNotificationIdentifier == null || userNotificationCollectionIdentifiers.includes(userNotificationIdentifier)) {
          return false;
        }
        userNotificationCollectionIdentifiers.push(userNotificationIdentifier);
        return true;
      });
      return [...userNotificationsToAdd, ...userNotificationCollection];
    }
    return userNotificationCollection;
  }

  protected convertDateFromClient(userNotification: IUserNotification): IUserNotification {
    return Object.assign({}, userNotification, {
      dateAdded: userNotification.dateAdded?.isValid() ? userNotification.dateAdded.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAdded = res.body.dateAdded ? dayjs(res.body.dateAdded) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userNotification: IUserNotification) => {
        userNotification.dateAdded = userNotification.dateAdded ? dayjs(userNotification.dateAdded) : undefined;
      });
    }
    return res;
  }
}
