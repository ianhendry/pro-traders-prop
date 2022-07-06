import dayjs from 'dayjs/esm';
import { ITradersChallengeTracker } from 'app/entities/traders-challenge-tracker/traders-challenge-tracker.model';

export interface IUserNotification {
  id?: number;
  commentTitle?: string;
  commentBody?: string | null;
  commentMediaContentType?: string | null;
  commentMedia?: string | null;
  dateAdded?: dayjs.Dayjs;
  makePublicVisibleOnSite?: boolean | null;
  tradersChallengeTracker?: ITradersChallengeTracker | null;
}

export class UserNotification implements IUserNotification {
  constructor(
    public id?: number,
    public commentTitle?: string,
    public commentBody?: string | null,
    public commentMediaContentType?: string | null,
    public commentMedia?: string | null,
    public dateAdded?: dayjs.Dayjs,
    public makePublicVisibleOnSite?: boolean | null,
    public tradersChallengeTracker?: ITradersChallengeTracker | null
  ) {
    this.makePublicVisibleOnSite = this.makePublicVisibleOnSite ?? false;
  }
}

export function getUserNotificationIdentifier(userNotification: IUserNotification): number | undefined {
  return userNotification.id;
}
