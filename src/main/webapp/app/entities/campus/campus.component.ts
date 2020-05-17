import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICampus } from 'app/shared/model/campus.model';
import { CampusService } from './campus.service';
import { CampusDeleteDialogComponent } from './campus-delete-dialog.component';

@Component({
  selector: 'jhi-campus',
  templateUrl: './campus.component.html'
})
export class CampusComponent implements OnInit, OnDestroy {
  campuses?: ICampus[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected campusService: CampusService,
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
      this.campusService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICampus[]>) => (this.campuses = res.body || []));
      return;
    }

    this.campusService.query().subscribe((res: HttpResponse<ICampus[]>) => (this.campuses = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCampuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICampus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCampuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('campusListModification', () => this.loadAll());
  }

  delete(campus: ICampus): void {
    const modalRef = this.modalService.open(CampusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.campus = campus;
  }
}
