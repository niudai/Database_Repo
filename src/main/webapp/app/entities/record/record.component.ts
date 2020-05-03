import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecord } from 'app/shared/model/record.model';
import { RecordService } from './record.service';
import { RecordDeleteDialogComponent } from './record-delete-dialog.component';

@Component({
  selector: 'jhi-record',
  templateUrl: './record.component.html'
})
export class RecordComponent implements OnInit, OnDestroy {
  records?: IRecord[];
  eventSubscriber?: Subscription;

  constructor(protected recordService: RecordService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.recordService.query().subscribe((res: HttpResponse<IRecord[]>) => (this.records = res.body || []));
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
