import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRecord, Record } from 'app/shared/model/record.model';
import { RecordService } from './record.service';
import { ISemaster } from 'app/shared/model/semaster.model';
import { SemasterService } from 'app/entities/semaster/semaster.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course/course.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

type SelectableEntity = ISemaster | ICourse | IStudent;

@Component({
  selector: 'jhi-record-update',
  templateUrl: './record-update.component.html',
})
export class RecordUpdateComponent implements OnInit {
  isSaving = false;
  semasters: ISemaster[] = [];
  courses: ICourse[] = [];
  students: IStudent[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    score: [],
    semaster: [],
    course: [],
    student: [],
  });

  constructor(
    protected recordService: RecordService,
    protected semasterService: SemasterService,
    protected courseService: CourseService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ record }) => {
      this.updateForm(record);

      this.semasterService.query().subscribe((res: HttpResponse<ISemaster[]>) => (this.semasters = res.body || []));

      this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => (this.courses = res.body || []));

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));
    });
  }

  updateForm(record: IRecord): void {
    this.editForm.patchValue({
      id: record.id,
      date: record.date,
      score: record.score,
      semaster: record.semaster,
      course: record.course,
      student: record.student,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const record = this.createFromForm();
    if (record.id !== undefined) {
      this.subscribeToSaveResponse(this.recordService.update(record));
    } else {
      this.subscribeToSaveResponse(this.recordService.create(record));
    }
  }

  private createFromForm(): IRecord {
    return {
      ...new Record(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      score: this.editForm.get(['score'])!.value,
      semaster: this.editForm.get(['semaster'])!.value,
      course: this.editForm.get(['course'])!.value,
      student: this.editForm.get(['student'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecord>>): void {
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
