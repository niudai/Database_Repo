import { Moment } from 'moment';
import { ITeacher } from 'app/shared/model/teacher.model';
import { IStudent } from 'app/shared/model/student.model';
import { IException } from 'app/shared/model/exception.model';
import { IGrade } from 'app/shared/model/grade.model';
import { IMajor } from 'app/shared/model/major.model';

export interface ISchoolClass {
  id?: number;
  name?: string;
  createdDate?: Moment;
  master?: ITeacher;
  students?: IStudent[];
  exceptions?: IException[];
  exceptions?: IException[];
  grade?: IGrade;
  major?: IMajor;
}

export class SchoolClass implements ISchoolClass {
  constructor(
    public id?: number,
    public name?: string,
    public createdDate?: Moment,
    public master?: ITeacher,
    public students?: IStudent[],
    public exceptions?: IException[],
    public exceptions?: IException[],
    public grade?: IGrade,
    public major?: IMajor
  ) {}
}
