import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMajor } from 'app/shared/model/major.model';
import { MajorService } from './major.service';
import { MajorDeleteDialogComponent } from './major-delete-dialog.component';

@Component({
  selector: 'jhi-major',
  templateUrl: './major.component.html',
})
export class MajorComponent implements OnInit, OnDestroy {
  majors?: IMajor[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected majorService: MajorService,
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
      this.majorService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IMajor[]>) => (this.majors = res.body || []));
      return;
    }

    this.majorService.query().subscribe((res: HttpResponse<IMajor[]>) => (this.majors = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMajors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMajor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMajors(): void {
    this.eventSubscriber = this.eventManager.subscribe('majorListModification', () => this.loadAll());
  }

  delete(major: IMajor): void {
    const modalRef = this.modalService.open(MajorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.major = major;
  }
}
