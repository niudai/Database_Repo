import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInterval } from 'app/shared/model/interval.model';
import { IntervalService } from './interval.service';
import { IntervalDeleteDialogComponent } from './interval-delete-dialog.component';

@Component({
  selector: 'jhi-interval',
  templateUrl: './interval.component.html',
})
export class IntervalComponent implements OnInit, OnDestroy {
  intervals?: IInterval[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected intervalService: IntervalService,
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
      this.intervalService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IInterval[]>) => (this.intervals = res.body || []));
      return;
    }

    this.intervalService.query().subscribe((res: HttpResponse<IInterval[]>) => (this.intervals = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIntervals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInterval): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIntervals(): void {
    this.eventSubscriber = this.eventManager.subscribe('intervalListModification', () => this.loadAll());
  }

  delete(interval: IInterval): void {
    const modalRef = this.modalService.open(IntervalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.interval = interval;
  }
}
