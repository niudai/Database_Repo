import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInterval } from 'app/shared/model/interval.model';

@Component({
  selector: 'jhi-interval-detail',
  templateUrl: './interval-detail.component.html'
})
export class IntervalDetailComponent implements OnInit {
  interval: IInterval | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interval }) => (this.interval = interval));
  }

  previousState(): void {
    window.history.back();
  }
}
