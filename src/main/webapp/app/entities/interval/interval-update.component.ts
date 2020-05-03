import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInterval, Interval } from 'app/shared/model/interval.model';
import { IntervalService } from './interval.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course/course.service';

@Component({
  selector: 'jhi-interval-update',
  templateUrl: './interval-update.component.html'
})
export class IntervalUpdateComponent implements OnInit {
  isSaving = false;
  courses: ICourse[] = [];

  editForm = this.fb.group({
    id: [],
    day: [],
    start: [],
    end: [],
    course: []
  });

  constructor(
    protected intervalService: IntervalService,
    protected courseService: CourseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interval }) => {
      this.updateForm(interval);

      this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => (this.courses = res.body || []));
    });
  }

  updateForm(interval: IInterval): void {
    this.editForm.patchValue({
      id: interval.id,
      day: interval.day,
      start: interval.start,
      end: interval.end,
      course: interval.course
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interval = this.createFromForm();
    if (interval.id !== undefined) {
      this.subscribeToSaveResponse(this.intervalService.update(interval));
    } else {
      this.subscribeToSaveResponse(this.intervalService.create(interval));
    }
  }

  private createFromForm(): IInterval {
    return {
      ...new Interval(),
      id: this.editForm.get(['id'])!.value,
      day: this.editForm.get(['day'])!.value,
      start: this.editForm.get(['start'])!.value,
      end: this.editForm.get(['end'])!.value,
      course: this.editForm.get(['course'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterval>>): void {
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

  trackById(index: number, item: ICourse): any {
    return item.id;
  }
}
