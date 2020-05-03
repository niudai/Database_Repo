import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISemaster } from 'app/shared/model/semaster.model';

@Component({
  selector: 'jhi-semaster-detail',
  templateUrl: './semaster-detail.component.html'
})
export class SemasterDetailComponent implements OnInit {
  semaster: ISemaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ semaster }) => (this.semaster = semaster));
  }

  previousState(): void {
    window.history.back();
  }
}
