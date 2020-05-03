import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IException } from 'app/shared/model/exception.model';
import { ExceptionService } from './exception.service';
import { ExceptionDeleteDialogComponent } from './exception-delete-dialog.component';

@Component({
  selector: 'jhi-exception',
  templateUrl: './exception.component.html'
})
export class ExceptionComponent implements OnInit, OnDestroy {
  exceptions?: IException[];
  eventSubscriber?: Subscription;

  constructor(protected exceptionService: ExceptionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.exceptionService.query().subscribe((res: HttpResponse<IException[]>) => (this.exceptions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExceptions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IException): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExceptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('exceptionListModification', () => this.loadAll());
  }

  delete(exception: IException): void {
    const modalRef = this.modalService.open(ExceptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.exception = exception;
  }
}
