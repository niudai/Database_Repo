import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJException, JException } from 'app/shared/model/j-exception.model';
import { JExceptionService } from './j-exception.service';
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
  selector: 'jhi-j-exception-update',
  templateUrl: './j-exception-update.component.html'
})
export class JExceptionUpdateComponent implements OnInit {
  isSaving = false;
  majors: IMajor[] = [];
  schoolclasses: ISchoolClass[] = [];
  grades: IGrade[] = [];
  students: IStudent[] = [];
  dateDp: any;

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
    protected jExceptionService: JExceptionService,
    protected majorService: MajorService,
    protected schoolClassService: SchoolClassService,
    protected gradeService: GradeService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jException }) => {
      this.updateForm(jException);

      this.majorService.query().subscribe((res: HttpResponse<IMajor[]>) => (this.majors = res.body || []));

      this.schoolClassService.query().subscribe((res: HttpResponse<ISchoolClass[]>) => (this.schoolclasses = res.body || []));

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));
    });
  }

  updateForm(jException: IJException): void {
    this.editForm.patchValue({
      id: jException.id,
      date: jException.date,
      isYouthLeague: jException.isYouthLeague,
      cause: jException.cause,
      originalMajor: jException.originalMajor,
      newMajor: jException.newMajor,
      originalSchoolClass: jException.originalSchoolClass,
      newSchoolClass: jException.newSchoolClass,
      originalGrade: jException.originalGrade,
      newGrade: jException.newGrade,
      student: jException.student
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jException = this.createFromForm();
    if (jException.id !== undefined) {
      this.subscribeToSaveResponse(this.jExceptionService.update(jException));
    } else {
      this.subscribeToSaveResponse(this.jExceptionService.create(jException));
    }
  }

  private createFromForm(): IJException {
    return {
      ...new JException(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJException>>): void {
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
