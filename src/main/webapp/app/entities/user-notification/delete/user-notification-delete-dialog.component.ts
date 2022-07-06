import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserNotification } from '../user-notification.model';
import { UserNotificationService } from '../service/user-notification.service';

@Component({
  templateUrl: './user-notification-delete-dialog.component.html',
})
export class UserNotificationDeleteDialogComponent {
  userNotification?: IUserNotification;

  constructor(protected userNotificationService: UserNotificationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userNotificationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
