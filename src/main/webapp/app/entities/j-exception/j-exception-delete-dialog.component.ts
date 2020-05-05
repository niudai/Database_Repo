import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJException } from 'app/shared/model/j-exception.model';
import { JExceptionService } from './j-exception.service';

@Component({
  templateUrl: './j-exception-delete-dialog.component.html'
})
export class JExceptionDeleteDialogComponent {
  jException?: IJException;

  constructor(
    protected jExceptionService: JExceptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jExceptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jExceptionListModification');
      this.activeModal.close();
    });
  }
}
