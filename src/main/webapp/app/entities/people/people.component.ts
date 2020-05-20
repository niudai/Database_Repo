import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { PeopleDeleteDialogComponent } from './people-delete-dialog.component';

@Component({
  selector: 'jhi-people',
  templateUrl: './people.component.html',
})
export class PeopleComponent implements OnInit, OnDestroy {
  people?: IPeople[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected peopleService: PeopleService,
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
      this.peopleService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body || []));
      return;
    }

    this.peopleService.query().subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPeople();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPeople): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPeople(): void {
    this.eventSubscriber = this.eventManager.subscribe('peopleListModification', () => this.loadAll());
  }

  delete(people: IPeople): void {
    const modalRef = this.modalService.open(PeopleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.people = people;
  }
}
