import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecord, Record } from 'app/shared/model/record.model';
import { RecordService } from './record.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';
import { ISemaster } from 'app/shared/model/semaster.model';
import { SemasterService } from 'app/entities/semaster/semaster.service';

type SelectableEntity = IStudent | ISemaster;

@Component({
  selector: 'jhi-record-update',
  templateUrl: './record-update.component.html'
})
export class RecordUpdateComponent implements OnInit {
  isSaving = false;
  students: IStudent[] = [];
  semasters: ISemaster[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    score: [],
    student: [],
    semaster: []
  });

  constructor(
    protected recordService: RecordService,
    protected studentService: StudentService,
    protected semasterService: SemasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ record }) => {
      if (!record.id) {
        const today = moment().startOf('day');
        record.date = today;
      }

      this.updateForm(record);

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));

      this.semasterService.query().subscribe((res: HttpResponse<ISemaster[]>) => (this.semasters = res.body || []));
    });
  }

  updateForm(record: IRecord): void {
    this.editForm.patchValue({
      id: record.id,
      date: record.date ? record.date.format(DATE_TIME_FORMAT) : null,
      score: record.score,
      student: record.student,
      semaster: record.semaster
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
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      score: this.editForm.get(['score'])!.value,
      student: this.editForm.get(['student'])!.value,
      semaster: this.editForm.get(['semaster'])!.value
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
