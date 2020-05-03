import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IException } from 'app/shared/model/exception.model';

@Component({
  selector: 'jhi-exception-detail',
  templateUrl: './exception-detail.component.html'
})
export class ExceptionDetailComponent implements OnInit {
  exception: IException | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exception }) => (this.exception = exception));
  }

  previousState(): void {
    window.history.back();
  }
}
