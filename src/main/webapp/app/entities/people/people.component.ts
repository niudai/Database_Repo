import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { PeopleDeleteDialogComponent } from './people-delete-dialog.component';

@Component({
  selector: 'jhi-people',
  templateUrl: './people.component.html'
})
export class PeopleComponent implements OnInit, OnDestroy {
  people?: IPeople[];
  eventSubscriber?: Subscription;

  constructor(protected peopleService: PeopleService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.peopleService.query().subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body || []));
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
