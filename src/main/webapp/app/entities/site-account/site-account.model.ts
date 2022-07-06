import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IBillingDetails } from 'app/entities/billing-details/billing-details.model';

export interface ISiteAccount {
  id?: number;
  accountName?: string | null;
  userPictureContentType?: string | null;
  userPicture?: string | null;
  userBio?: string | null;
  inActive?: boolean | null;
  inActiveDate?: dayjs.Dayjs | null;
  addressDetails?: IBillingDetails | null;
  user?: IUser | null;
}

export class SiteAccount implements ISiteAccount {
  constructor(
    public id?: number,
    public accountName?: string | null,
    public userPictureContentType?: string | null,
    public userPicture?: string | null,
    public userBio?: string | null,
    public inActive?: boolean | null,
    public inActiveDate?: dayjs.Dayjs | null,
    public addressDetails?: IBillingDetails | null,
    public user?: IUser | null
  ) {
    this.inActive = this.inActive ?? false;
  }
}

export function getSiteAccountIdentifier(siteAccount: ISiteAccount): number | undefined {
  return siteAccount.id;
}
