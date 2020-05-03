import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISemaster, Semaster } from 'app/shared/model/semaster.model';
import { SemasterService } from './semaster.service';

@Component({
  selector: 'jhi-semaster-update',
  templateUrl: './semaster-update.component.html'
})
export class SemasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    year: [],
    season: []
  });

  constructor(protected semasterService: SemasterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ semaster }) => {
      this.updateForm(semaster);
    });
  }

  updateForm(semaster: ISemaster): void {
    this.editForm.patchValue({
      id: semaster.id,
      year: semaster.year,
      season: semaster.season
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const semaster = this.createFromForm();
    if (semaster.id !== undefined) {
      this.subscribeToSaveResponse(this.semasterService.update(semaster));
    } else {
      this.subscribeToSaveResponse(this.semasterService.create(semaster));
    }
  }

  private createFromForm(): ISemaster {
    return {
      ...new Semaster(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      season: this.editForm.get(['season'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISemaster>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
