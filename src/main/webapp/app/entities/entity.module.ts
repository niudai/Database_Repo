import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'campus',
        loadChildren: () => import('./campus/campus.module').then(m => m.JhipsterCampusModule),
      },
      {
        path: 'major',
        loadChildren: () => import('./major/major.module').then(m => m.JhipsterMajorModule),
      },
      {
        path: 'record',
        loadChildren: () => import('./record/record.module').then(m => m.JhipsterRecordModule),
      },
      {
        path: 'school-class',
        loadChildren: () => import('./school-class/school-class.module').then(m => m.JhipsterSchoolClassModule),
      },
      {
        path: 'grade',
        loadChildren: () => import('./grade/grade.module').then(m => m.JhipsterGradeModule),
      },
      {
        path: 'people',
        loadChildren: () => import('./people/people.module').then(m => m.JhipsterPeopleModule),
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.JhipsterStudentModule),
      },
      {
        path: 'teacher',
        loadChildren: () => import('./teacher/teacher.module').then(m => m.JhipsterTeacherModule),
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.JhipsterCourseModule),
      },
      {
        path: 'interval',
        loadChildren: () => import('./interval/interval.module').then(m => m.JhipsterIntervalModule),
      },
      {
        path: 'semaster',
        loadChildren: () => import('./semaster/semaster.module').then(m => m.JhipsterSemasterModule),
      },
      {
        path: 'j-exception',
        loadChildren: () => import('./j-exception/j-exception.module').then(m => m.JhipsterJExceptionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterEntityModule {}
