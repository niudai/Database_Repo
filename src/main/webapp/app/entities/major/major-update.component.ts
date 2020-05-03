import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMajor, Major } from 'app/shared/model/major.model';
import { MajorService } from './major.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { ICampus } from 'app/shared/model/campus.model';
import { CampusService } from 'app/entities/campus/campus.service';

type SelectableEntity = IPeople | ICampus;

@Component({
  selector: 'jhi-major-update',
  templateUrl: './major-update.component.html'
})
export class MajorUpdateComponent implements OnInit {
  isSaving = false;
  managers: IPeople[] = [];
  campuses: ICampus[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    manager: [],
    campus: []
  });

  constructor(
    protected majorService: MajorService,
    protected peopleService: PeopleService,
    protected campusService: CampusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ major }) => {
      this.updateForm(major);

      this.peopleService
        .query({ filter: 'managemajor-is-null' })
        .pipe(
          map((res: HttpResponse<IPeople[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPeople[]) => {
          if (!major.manager || !major.manager.id) {
            this.managers = resBody;
          } else {
            this.peopleService
              .find(major.manager.id)
              .pipe(
                map((subRes: HttpResponse<IPeople>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPeople[]) => (this.managers = concatRes));
          }
        });

      this.campusService.query().subscribe((res: HttpResponse<ICampus[]>) => (this.campuses = res.body || []));
    });
  }

  updateForm(major: IMajor): void {
    this.editForm.patchValue({
      id: major.id,
      name: major.name,
      manager: major.manager,
      campus: major.campus
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
      manager: this.editForm.get(['manager'])!.value,
      campus: this.editForm.get(['campus'])!.value
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
