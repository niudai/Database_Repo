import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { ISchoolClass } from 'app/shared/model/school-class.model';
import { SchoolClassService } from 'app/entities/school-class/school-class.service';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  schoolclasses: ISchoolClass[] = [];

  editForm = this.fb.group({
    id: [],
    studentNumber: [],
    startDate: [],
    email: [],
    major: [],
    schoolClass: []
  });

  constructor(
    protected studentService: StudentService,
    protected schoolClassService: SchoolClassService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);

      this.schoolClassService.query().subscribe((res: HttpResponse<ISchoolClass[]>) => (this.schoolclasses = res.body || []));
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      studentNumber: student.studentNumber,
      startDate: student.startDate,
      email: student.email,
      major: student.major,
      schoolClass: student.schoolClass
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      studentNumber: this.editForm.get(['studentNumber'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      email: this.editForm.get(['email'])!.value,
      major: this.editForm.get(['major'])!.value,
      schoolClass: this.editForm.get(['schoolClass'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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

  trackById(index: number, item: ISchoolClass): any {
    return item.id;
  }
}
