import dayjs from 'dayjs/esm';
import { IMt4Account } from 'app/entities/mt-4-account/mt-4-account.model';
import { TRADEDIRECTION } from 'app/entities/enumerations/tradedirection.model';

export interface IMt4Trade {
  id?: number;
  ticket?: number;
  openTime?: dayjs.Dayjs | null;
  directionType?: TRADEDIRECTION | null;
  positionSize?: number | null;
  symbol?: string | null;
  openPrice?: number | null;
  stopLossPrice?: number | null;
  takeProfitPrice?: number | null;
  closeTime?: dayjs.Dayjs | null;
  closePrice?: number | null;
  commission?: number | null;
  taxes?: number | null;
  swap?: number | null;
  profit?: number | null;
  mt4Account?: IMt4Account | null;
}

export class Mt4Trade implements IMt4Trade {
  constructor(
    public id?: number,
    public ticket?: number,
    public openTime?: dayjs.Dayjs | null,
    public directionType?: TRADEDIRECTION | null,
    public positionSize?: number | null,
    public symbol?: string | null,
    public openPrice?: number | null,
    public stopLossPrice?: number | null,
    public takeProfitPrice?: number | null,
    public closeTime?: dayjs.Dayjs | null,
    public closePrice?: number | null,
    public commission?: number | null,
    public taxes?: number | null,
    public swap?: number | null,
    public profit?: number | null,
    public mt4Account?: IMt4Account | null
  ) {}
}

export function getMt4TradeIdentifier(mt4Trade: IMt4Trade): number | undefined {
  return mt4Trade.id;
}
