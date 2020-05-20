import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJException } from 'app/shared/model/j-exception.model';

@Component({
  selector: 'jhi-j-exception-detail',
  templateUrl: './j-exception-detail.component.html',
})
export class JExceptionDetailComponent implements OnInit {
  jException: IJException | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jException }) => (this.jException = jException));
  }

  previousState(): void {
    window.history.back();
  }
}
