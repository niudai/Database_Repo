import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecord } from 'app/shared/model/record.model';
import { RecordService } from './record.service';
import { RecordDeleteDialogComponent } from './record-delete-dialog.component';

@Component({
  selector: 'jhi-record',
  templateUrl: './record.component.html',
})
export class RecordComponent implements OnInit, OnDestroy {
  records?: IRecord[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected recordService: RecordService,
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
      this.recordService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IRecord[]>) => (this.records = res.body || []));
      return;
    }

    this.recordService.query().subscribe((res: HttpResponse<IRecord[]>) => (this.records = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRecords();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRecord): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRecords(): void {
    this.eventSubscriber = this.eventManager.subscribe('recordListModification', () => this.loadAll());
  }

  delete(record: IRecord): void {
    const modalRef = this.modalService.open(RecordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.record = record;
  }
}
