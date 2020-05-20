import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchoolClass } from 'app/shared/model/school-class.model';
import { SchoolClassService } from './school-class.service';

@Component({
  templateUrl: './school-class-delete-dialog.component.html',
})
export class SchoolClassDeleteDialogComponent {
  schoolClass?: ISchoolClass;

  constructor(
    protected schoolClassService: SchoolClassService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schoolClassService.delete(id).subscribe(() => {
      this.eventManager.broadcast('schoolClassListModification');
      this.activeModal.close();
    });
  }
}
