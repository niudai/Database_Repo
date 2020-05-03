import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInterval } from 'app/shared/model/interval.model';
import { IntervalService } from './interval.service';

@Component({
  templateUrl: './interval-delete-dialog.component.html'
})
export class IntervalDeleteDialogComponent {
  interval?: IInterval;

  constructor(protected intervalService: IntervalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.intervalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('intervalListModification');
      this.activeModal.close();
    });
  }
}
