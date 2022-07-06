import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'site-account',
        data: { pageTitle: 'SiteAccounts' },
        loadChildren: () => import('./site-account/site-account.module').then(m => m.SiteAccountModule),
      },
      {
        path: 'challenge-type',
        data: { pageTitle: 'ChallengeTypes' },
        loadChildren: () => import('./challenge-type/challenge-type.module').then(m => m.ChallengeTypeModule),
      },
      {
        path: 'mt-4-account',
        data: { pageTitle: 'Mt4Accounts' },
        loadChildren: () => import('./mt-4-account/mt-4-account.module').then(m => m.Mt4AccountModule),
      },
      {
        path: 'billing-details',
        data: { pageTitle: 'BillingDetails' },
        loadChildren: () => import('./billing-details/billing-details.module').then(m => m.BillingDetailsModule),
      },
      {
        path: 'trade-challenge',
        data: { pageTitle: 'TradeChallenges' },
        loadChildren: () => import('./trade-challenge/trade-challenge.module').then(m => m.TradeChallengeModule),
      },
      {
        path: 'user-notification',
        data: { pageTitle: 'UserNotifications' },
        loadChildren: () => import('./user-notification/user-notification.module').then(m => m.UserNotificationModule),
      },
      {
        path: 'mt-4-trade',
        data: { pageTitle: 'Mt4Trades' },
        loadChildren: () => import('./mt-4-trade/mt-4-trade.module').then(m => m.Mt4TradeModule),
      },
      {
        path: 'account-data-time-series',
        data: { pageTitle: 'AccountDataTimeSeries' },
        loadChildren: () => import('./account-data-time-series/account-data-time-series.module').then(m => m.AccountDataTimeSeriesModule),
      },
      {
        path: 'traders-challenge-tracker',
        data: { pageTitle: 'TradersChallengeTrackers' },
        loadChildren: () =>
          import('./traders-challenge-tracker/traders-challenge-tracker.module').then(m => m.TradersChallengeTrackerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
