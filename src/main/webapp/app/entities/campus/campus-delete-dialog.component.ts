import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICampus } from 'app/shared/model/campus.model';
import { CampusService } from './campus.service';

@Component({
  templateUrl: './campus-delete-dialog.component.html',
})
export class CampusDeleteDialogComponent {
  campus?: ICampus;

  constructor(protected campusService: CampusService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.campusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('campusListModification');
      this.activeModal.close();
    });
  }
}
