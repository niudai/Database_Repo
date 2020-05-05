import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJException } from 'app/shared/model/j-exception.model';
import { JExceptionService } from './j-exception.service';
import { JExceptionDeleteDialogComponent } from './j-exception-delete-dialog.component';

@Component({
  selector: 'jhi-j-exception',
  templateUrl: './j-exception.component.html'
})
export class JExceptionComponent implements OnInit, OnDestroy {
  jExceptions?: IJException[];
  eventSubscriber?: Subscription;

  constructor(protected jExceptionService: JExceptionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.jExceptionService.query().subscribe((res: HttpResponse<IJException[]>) => (this.jExceptions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJExceptions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJException): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJExceptions(): void {
    this.eventSubscriber = this.eventManager.subscribe('jExceptionListModification', () => this.loadAll());
  }

  delete(jException: IJException): void {
    const modalRef = this.modalService.open(JExceptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jException = jException;
  }
}
