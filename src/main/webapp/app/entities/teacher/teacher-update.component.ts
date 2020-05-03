import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITeacher, Teacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';
import { IMajor } from 'app/shared/model/major.model';
import { MajorService } from 'app/entities/major/major.service';

@Component({
  selector: 'jhi-teacher-update',
  templateUrl: './teacher-update.component.html'
})
export class TeacherUpdateComponent implements OnInit {
  isSaving = false;
  majors: IMajor[] = [];

  editForm = this.fb.group({
    id: [],
    workNumber: [],
    startDate: [],
    email: [],
    title: [],
    major: []
  });

  constructor(
    protected teacherService: TeacherService,
    protected majorService: MajorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teacher }) => {
      if (!teacher.id) {
        const today = moment().startOf('day');
        teacher.startDate = today;
      }

      this.updateForm(teacher);

      this.majorService.query().subscribe((res: HttpResponse<IMajor[]>) => (this.majors = res.body || []));
    });
  }

  updateForm(teacher: ITeacher): void {
    this.editForm.patchValue({
      id: teacher.id,
      workNumber: teacher.workNumber,
      startDate: teacher.startDate ? teacher.startDate.format(DATE_TIME_FORMAT) : null,
      email: teacher.email,
      title: teacher.title,
      major: teacher.major
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teacher = this.createFromForm();
    if (teacher.id !== undefined) {
      this.subscribeToSaveResponse(this.teacherService.update(teacher));
    } else {
      this.subscribeToSaveResponse(this.teacherService.create(teacher));
    }
  }

  private createFromForm(): ITeacher {
    return {
      ...new Teacher(),
      id: this.editForm.get(['id'])!.value,
      workNumber: this.editForm.get(['workNumber'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      email: this.editForm.get(['email'])!.value,
      title: this.editForm.get(['title'])!.value,
      major: this.editForm.get(['major'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacher>>): void {
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

  trackById(index: number, item: IMajor): any {
    return item.id;
  }
}
