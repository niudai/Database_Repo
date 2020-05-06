import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISchoolClass, SchoolClass } from 'app/shared/model/school-class.model';
import { SchoolClassService } from './school-class.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher/teacher.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IMajor } from 'app/shared/model/major.model';
import { MajorService } from 'app/entities/major/major.service';

type SelectableEntity = ITeacher | IGrade | IMajor;

@Component({
  selector: 'jhi-school-class-update',
  templateUrl: './school-class-update.component.html'
})
export class SchoolClassUpdateComponent implements OnInit {
  isSaving = false;
  masters: ITeacher[] = [];
  grades: IGrade[] = [];
  majors: IMajor[] = [];
  createdDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    createdDate: [],
    master: [],
    grade: [],
    major: []
  });

  constructor(
    protected schoolClassService: SchoolClassService,
    protected teacherService: TeacherService,
    protected gradeService: GradeService,
    protected majorService: MajorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schoolClass }) => {
      this.updateForm(schoolClass);

      this.teacherService
        .query({ filter: 'schoolclass-is-null' })
        .pipe(
          map((res: HttpResponse<ITeacher[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITeacher[]) => {
          if (!schoolClass.master || !schoolClass.master.id) {
            this.masters = resBody;
          } else {
            this.teacherService
              .find(schoolClass.master.id)
              .pipe(
                map((subRes: HttpResponse<ITeacher>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITeacher[]) => (this.masters = concatRes));
          }
        });

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));

      this.majorService.query().subscribe((res: HttpResponse<IMajor[]>) => (this.majors = res.body || []));
    });
  }

  updateForm(schoolClass: ISchoolClass): void {
    this.editForm.patchValue({
      id: schoolClass.id,
      name: schoolClass.name,
      createdDate: schoolClass.createdDate,
      master: schoolClass.master,
      grade: schoolClass.grade,
      major: schoolClass.major
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schoolClass = this.createFromForm();
    if (schoolClass.id !== undefined) {
      this.subscribeToSaveResponse(this.schoolClassService.update(schoolClass));
    } else {
      this.subscribeToSaveResponse(this.schoolClassService.create(schoolClass));
    }
  }

  private createFromForm(): ISchoolClass {
    return {
      ...new SchoolClass(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      master: this.editForm.get(['master'])!.value,
      grade: this.editForm.get(['grade'])!.value,
      major: this.editForm.get(['major'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchoolClass>>): void {
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
