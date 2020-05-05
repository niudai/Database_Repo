import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IException, Exception } from 'app/shared/model/exception.model';
import { ExceptionService } from './exception.service';
import { IMajor } from 'app/shared/model/major.model';
import { MajorService } from 'app/entities/major/major.service';
import { ISchoolClass } from 'app/shared/model/school-class.model';
import { SchoolClassService } from 'app/entities/school-class/school-class.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

type SelectableEntity = IMajor | ISchoolClass | IGrade | IStudent;

@Component({
  selector: 'jhi-exception-update',
  templateUrl: './exception-update.component.html'
})
export class ExceptionUpdateComponent implements OnInit {
  isSaving = false;
  majors: IMajor[] = [];
  schoolclasses: ISchoolClass[] = [];
  grades: IGrade[] = [];
  students: IStudent[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    isYouthLeague: [],
    cause: [],
    originalMajor: [],
    newMajor: [],
    originalSchoolClass: [],
    newSchoolClass: [],
    originalGrade: [],
    newGrade: [],
    student: []
  });

  constructor(
    protected exceptionService: ExceptionService,
    protected majorService: MajorService,
    protected schoolClassService: SchoolClassService,
    protected gradeService: GradeService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exception }) => {
      if (!exception.id) {
        const today = moment().startOf('day');
        exception.date = today;
      }

      this.updateForm(exception);

      this.majorService.query().subscribe((res: HttpResponse<IMajor[]>) => (this.majors = res.body || []));

      this.schoolClassService.query().subscribe((res: HttpResponse<ISchoolClass[]>) => (this.schoolclasses = res.body || []));

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));
    });
  }

  updateForm(exception: IException): void {
    this.editForm.patchValue({
      id: exception.id,
      date: exception.date ? exception.date.format(DATE_TIME_FORMAT) : null,
      isYouthLeague: exception.isYouthLeague,
      cause: exception.cause,
      originalMajor: exception.originalMajor,
      newMajor: exception.newMajor,
      originalSchoolClass: exception.originalSchoolClass,
      newSchoolClass: exception.newSchoolClass,
      originalGrade: exception.originalGrade,
      newGrade: exception.newGrade,
      student: exception.student
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const exception = this.createFromForm();
    if (exception.id !== undefined) {
      this.subscribeToSaveResponse(this.exceptionService.update(exception));
    } else {
      this.subscribeToSaveResponse(this.exceptionService.create(exception));
    }
  }

  private createFromForm(): IException {
    return {
      ...new Exception(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      isYouthLeague: this.editForm.get(['isYouthLeague'])!.value,
      cause: this.editForm.get(['cause'])!.value,
      originalMajor: this.editForm.get(['originalMajor'])!.value,
      newMajor: this.editForm.get(['newMajor'])!.value,
      originalSchoolClass: this.editForm.get(['originalSchoolClass'])!.value,
      newSchoolClass: this.editForm.get(['newSchoolClass'])!.value,
      originalGrade: this.editForm.get(['originalGrade'])!.value,
      newGrade: this.editForm.get(['newGrade'])!.value,
      student: this.editForm.get(['student'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IException>>): void {
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
