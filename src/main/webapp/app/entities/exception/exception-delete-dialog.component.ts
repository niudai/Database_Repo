import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IException } from 'app/shared/model/exception.model';
import { ExceptionService } from './exception.service';

@Component({
  templateUrl: './exception-delete-dialog.component.html'
})
export class ExceptionDeleteDialogComponent {
  exception?: IException;

  constructor(protected exceptionService: ExceptionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.exceptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('exceptionListModification');
      this.activeModal.close();
    });
  }
}
