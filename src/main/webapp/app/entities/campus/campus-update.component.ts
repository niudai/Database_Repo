import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICampus, Campus } from 'app/shared/model/campus.model';
import { CampusService } from './campus.service';

@Component({
  selector: 'jhi-campus-update',
  templateUrl: './campus-update.component.html',
})
export class CampusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
  });

  constructor(protected campusService: CampusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campus }) => {
      this.updateForm(campus);
    });
  }

  updateForm(campus: ICampus): void {
    this.editForm.patchValue({
      id: campus.id,
      name: campus.name,
      address: campus.address,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const campus = this.createFromForm();
    if (campus.id !== undefined) {
      this.subscribeToSaveResponse(this.campusService.update(campus));
    } else {
      this.subscribeToSaveResponse(this.campusService.create(campus));
    }
  }

  private createFromForm(): ICampus {
    return {
      ...new Campus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampus>>): void {
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
