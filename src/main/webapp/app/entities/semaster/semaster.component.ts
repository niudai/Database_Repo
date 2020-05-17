import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISemaster } from 'app/shared/model/semaster.model';
import { SemasterService } from './semaster.service';
import { SemasterDeleteDialogComponent } from './semaster-delete-dialog.component';

@Component({
  selector: 'jhi-semaster',
  templateUrl: './semaster.component.html'
})
export class SemasterComponent implements OnInit, OnDestroy {
  semasters?: ISemaster[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected semasterService: SemasterService,
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
      this.semasterService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ISemaster[]>) => (this.semasters = res.body || []));
      return;
    }

    this.semasterService.query().subscribe((res: HttpResponse<ISemaster[]>) => (this.semasters = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSemasters();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISemaster): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSemasters(): void {
    this.eventSubscriber = this.eventManager.subscribe('semasterListModification', () => this.loadAll());
  }

  delete(semaster: ISemaster): void {
    const modalRef = this.modalService.open(SemasterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.semaster = semaster;
  }
}
