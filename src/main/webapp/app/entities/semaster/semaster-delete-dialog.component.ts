import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISemaster } from 'app/shared/model/semaster.model';
import { SemasterService } from './semaster.service';

@Component({
  templateUrl: './semaster-delete-dialog.component.html'
})
export class SemasterDeleteDialogComponent {
  semaster?: ISemaster;

  constructor(protected semasterService: SemasterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.semasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('semasterListModification');
      this.activeModal.close();
    });
  }
}
