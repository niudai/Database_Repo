import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICourse, Course } from 'app/shared/model/course.model';
import { CourseService } from './course.service';
import { ISemaster } from 'app/shared/model/semaster.model';
import { SemasterService } from 'app/entities/semaster/semaster.service';
import { IMajor } from 'app/shared/model/major.model';
import { MajorService } from 'app/entities/major/major.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher/teacher.service';

type SelectableEntity = ISemaster | IMajor | ITeacher;

@Component({
  selector: 'jhi-course-update',
  templateUrl: './course-update.component.html'
})
export class CourseUpdateComponent implements OnInit {
  isSaving = false;
  semasters: ISemaster[] = [];
  majors: IMajor[] = [];
  teachers: ITeacher[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    examType: [],
    semaster: [],
    major: [],
    teacher: []
  });

  constructor(
    protected courseService: CourseService,
    protected semasterService: SemasterService,
    protected majorService: MajorService,
    protected teacherService: TeacherService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.updateForm(course);

      this.semasterService.query().subscribe((res: HttpResponse<ISemaster[]>) => (this.semasters = res.body || []));

      this.majorService.query().subscribe((res: HttpResponse<IMajor[]>) => (this.majors = res.body || []));

      this.teacherService.query().subscribe((res: HttpResponse<ITeacher[]>) => (this.teachers = res.body || []));
    });
  }

  updateForm(course: ICourse): void {
    this.editForm.patchValue({
      id: course.id,
      name: course.name,
      examType: course.examType,
      semaster: course.semaster,
      major: course.major,
      teacher: course.teacher
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const course = this.createFromForm();
    if (course.id !== undefined) {
      this.subscribeToSaveResponse(this.courseService.update(course));
    } else {
      this.subscribeToSaveResponse(this.courseService.create(course));
    }
  }

  private createFromForm(): ICourse {
    return {
      ...new Course(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      examType: this.editForm.get(['examType'])!.value,
      semaster: this.editForm.get(['semaster'])!.value,
      major: this.editForm.get(['major'])!.value,
      teacher: this.editForm.get(['teacher'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>): void {
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
