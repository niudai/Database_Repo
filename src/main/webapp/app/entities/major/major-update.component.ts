import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMajor, Major } from 'app/shared/model/major.model';
import { MajorService } from './major.service';
import { ICampus } from 'app/shared/model/campus.model';
import { CampusService } from 'app/entities/campus/campus.service';

@Component({
  selector: 'jhi-major-update',
  templateUrl: './major-update.component.html',
})
export class MajorUpdateComponent implements OnInit {
  isSaving = false;
  campuses: ICampus[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    campus: [],
  });

  constructor(
    protected majorService: MajorService,
    protected campusService: CampusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ major }) => {
      this.updateForm(major);

      this.campusService.query().subscribe((res: HttpResponse<ICampus[]>) => (this.campuses = res.body || []));
    });
  }

  updateForm(major: IMajor): void {
    this.editForm.patchValue({
      id: major.id,
      name: major.name,
      campus: major.campus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const major = this.createFromForm();
    if (major.id !== undefined) {
      this.subscribeToSaveResponse(this.majorService.update(major));
    } else {
      this.subscribeToSaveResponse(this.majorService.create(major));
    }
  }

  private createFromForm(): IMajor {
    return {
      ...new Major(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      campus: this.editForm.get(['campus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMajor>>): void {
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

  trackById(index: number, item: ICampus): any {
    return item.id;
  }
}
