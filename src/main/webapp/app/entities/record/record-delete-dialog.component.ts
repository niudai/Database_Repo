import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecord } from 'app/shared/model/record.model';
import { RecordService } from './record.service';

@Component({
  templateUrl: './record-delete-dialog.component.html'
})
export class RecordDeleteDialogComponent {
  record?: IRecord;

  constructor(protected recordService: RecordService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recordListModification');
      this.activeModal.close();
    });
  }
}
