import dayjs from 'dayjs/esm';
import { IMt4Account } from 'app/entities/mt-4-account/mt-4-account.model';
import { ISiteAccount } from 'app/entities/site-account/site-account.model';
import { IChallengeType } from 'app/entities/challenge-type/challenge-type.model';
import { IUser } from 'app/entities/user/user.model';
import { IUserNotification } from 'app/entities/user-notification/user-notification.model';

export interface ITradeChallenge {
  id?: number;
  tradeChallengeName?: string | null;
  startDate?: dayjs.Dayjs | null;
  accountDayStartBalance?: number | null;
  accountDayStartEquity?: number | null;
  runningMaxTotalDrawdown?: number | null;
  runningMaxDailyDrawdown?: number | null;
  lowestDrawdownPoint?: number | null;
  rulesViolated?: boolean | null;
  ruleViolated?: string | null;
  ruleViolatedDate?: dayjs.Dayjs | null;
  mt4Account?: IMt4Account | null;
  siteAccount?: ISiteAccount | null;
  challengeType?: IChallengeType | null;
  user?: IUser | null;
  userNotifications?: IUserNotification[] | null;
}

export class TradeChallenge implements ITradeChallenge {
  constructor(
    public id?: number,
    public tradeChallengeName?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public accountDayStartBalance?: number | null,
    public accountDayStartEquity?: number | null,
    public runningMaxTotalDrawdown?: number | null,
    public runningMaxDailyDrawdown?: number | null,
    public lowestDrawdownPoint?: number | null,
    public rulesViolated?: boolean | null,
    public ruleViolated?: string | null,
    public ruleViolatedDate?: dayjs.Dayjs | null,
    public mt4Account?: IMt4Account | null,
    public siteAccount?: ISiteAccount | null,
    public challengeType?: IChallengeType | null,
    public user?: IUser | null,
    public userNotifications?: IUserNotification[] | null
  ) {
    this.rulesViolated = this.rulesViolated ?? false;
  }
}

export function getTradeChallengeIdentifier(tradeChallenge: ITradeChallenge): number | undefined {
  return tradeChallenge.id;
}
