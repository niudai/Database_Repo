import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchoolClass } from 'app/shared/model/school-class.model';
import { SchoolClassService } from './school-class.service';
import { SchoolClassDeleteDialogComponent } from './school-class-delete-dialog.component';

@Component({
  selector: 'jhi-school-class',
  templateUrl: './school-class.component.html'
})
export class SchoolClassComponent implements OnInit, OnDestroy {
  schoolClasses?: ISchoolClass[];
  eventSubscriber?: Subscription;

  constructor(
    protected schoolClassService: SchoolClassService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.schoolClassService.query().subscribe((res: HttpResponse<ISchoolClass[]>) => (this.schoolClasses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSchoolClasses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISchoolClass): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSchoolClasses(): void {
    this.eventSubscriber = this.eventManager.subscribe('schoolClassListModification', () => this.loadAll());
  }

  delete(schoolClass: ISchoolClass): void {
    const modalRef = this.modalService.open(SchoolClassDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.schoolClass = schoolClass;
  }
}
