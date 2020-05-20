import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMajor } from 'app/shared/model/major.model';
import { MajorService } from './major.service';

@Component({
  templateUrl: './major-delete-dialog.component.html',
})
export class MajorDeleteDialogComponent {
  major?: IMajor;

  constructor(protected majorService: MajorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.majorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('majorListModification');
      this.activeModal.close();
    });
  }
}
