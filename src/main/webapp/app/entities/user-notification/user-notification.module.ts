import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserNotificationComponent } from './list/user-notification.component';
import { UserNotificationDetailComponent } from './detail/user-notification-detail.component';
import { UserNotificationUpdateComponent } from './update/user-notification-update.component';
import { UserNotificationDeleteDialogComponent } from './delete/user-notification-delete-dialog.component';
import { UserNotificationRoutingModule } from './route/user-notification-routing.module';

@NgModule({
  imports: [SharedModule, UserNotificationRoutingModule],
  declarations: [
    UserNotificationComponent,
    UserNotificationDetailComponent,
    UserNotificationUpdateComponent,
    UserNotificationDeleteDialogComponent,
  ],
  entryComponents: [UserNotificationDeleteDialogComponent],
})
export class UserNotificationModule {}
