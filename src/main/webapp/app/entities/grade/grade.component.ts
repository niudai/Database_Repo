import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from './grade.service';
import { GradeDeleteDialogComponent } from './grade-delete-dialog.component';

@Component({
  selector: 'jhi-grade',
  templateUrl: './grade.component.html',
})
export class GradeComponent implements OnInit, OnDestroy {
  grades?: IGrade[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected gradeService: GradeService,
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
      this.gradeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));
      return;
    }

    this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGrades();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGrade): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGrades(): void {
    this.eventSubscriber = this.eventManager.subscribe('gradeListModification', () => this.loadAll());
  }

  delete(grade: IGrade): void {
    const modalRef = this.modalService.open(GradeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.grade = grade;
  }
}
