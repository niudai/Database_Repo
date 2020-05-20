import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchoolClass } from 'app/shared/model/school-class.model';
import { SchoolClassService } from './school-class.service';
import { SchoolClassDeleteDialogComponent } from './school-class-delete-dialog.component';

@Component({
  selector: 'jhi-school-class',
  templateUrl: './school-class.component.html',
})
export class SchoolClassComponent implements OnInit, OnDestroy {
  schoolClasses?: ISchoolClass[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected schoolClassService: SchoolClassService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.schoolClassService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ISchoolClass[]>) => (this.schoolClasses = res.body || []));
      return;
    }

    this.schoolClassService.query().subscribe((res: HttpResponse<ISchoolClass[]>) => (this.schoolClasses = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
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
