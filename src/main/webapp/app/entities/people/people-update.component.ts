import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPeople, People } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher/teacher.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

type SelectableEntity = ITeacher | IStudent;

@Component({
  selector: 'jhi-people-update',
  templateUrl: './people-update.component.html'
})
export class PeopleUpdateComponent implements OnInit {
  isSaving = false;
  teachers: ITeacher[] = [];
  students: IStudent[] = [];
  birthDateDp: any;

  editForm = this.fb.group({
    id: [],
    idType: [],
    chineseName: [],
    englishName: [],
    gender: [],
    birthDate: [],
    race: [],
    nation: [],
    address: [],
    postcode: [],
    telephone: [],
    teacher: [],
    student: []
  });

  constructor(
    protected peopleService: PeopleService,
    protected teacherService: TeacherService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ people }) => {
      this.updateForm(people);

      this.teacherService
        .query({ filter: 'people-is-null' })
        .pipe(
          map((res: HttpResponse<ITeacher[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITeacher[]) => {
          if (!people.teacher || !people.teacher.id) {
            this.teachers = resBody;
          } else {
            this.teacherService
              .find(people.teacher.id)
              .pipe(
                map((subRes: HttpResponse<ITeacher>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITeacher[]) => (this.teachers = concatRes));
          }
        });

      this.studentService
        .query({ filter: 'people-is-null' })
        .pipe(
          map((res: HttpResponse<IStudent[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IStudent[]) => {
          if (!people.student || !people.student.id) {
            this.students = resBody;
          } else {
            this.studentService
              .find(people.student.id)
              .pipe(
                map((subRes: HttpResponse<IStudent>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IStudent[]) => (this.students = concatRes));
          }
        });
    });
  }

  updateForm(people: IPeople): void {
    this.editForm.patchValue({
      id: people.id,
      idType: people.idType,
      chineseName: people.chineseName,
      englishName: people.englishName,
      gender: people.gender,
      birthDate: people.birthDate,
      race: people.race,
      nation: people.nation,
      address: people.address,
      postcode: people.postcode,
      telephone: people.telephone,
      teacher: people.teacher,
      student: people.student
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const people = this.createFromForm();
    if (people.id !== undefined) {
      this.subscribeToSaveResponse(this.peopleService.update(people));
    } else {
      this.subscribeToSaveResponse(this.peopleService.create(people));
    }
  }

  private createFromForm(): IPeople {
    return {
      ...new People(),
      id: this.editForm.get(['id'])!.value,
      idType: this.editForm.get(['idType'])!.value,
      chineseName: this.editForm.get(['chineseName'])!.value,
      englishName: this.editForm.get(['englishName'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      race: this.editForm.get(['race'])!.value,
      nation: this.editForm.get(['nation'])!.value,
      address: this.editForm.get(['address'])!.value,
      postcode: this.editForm.get(['postcode'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      teacher: this.editForm.get(['teacher'])!.value,
      student: this.editForm.get(['student'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeople>>): void {
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
