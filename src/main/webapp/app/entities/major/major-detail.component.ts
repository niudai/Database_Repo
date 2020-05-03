import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMajor } from 'app/shared/model/major.model';

@Component({
  selector: 'jhi-major-detail',
  templateUrl: './major-detail.component.html'
})
export class MajorDetailComponent implements OnInit {
  major: IMajor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ major }) => (this.major = major));
  }

  previousState(): void {
    window.history.back();
  }
}
