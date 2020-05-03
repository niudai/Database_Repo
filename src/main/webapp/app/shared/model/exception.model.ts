import { Moment } from 'moment';
import { IMajor } from 'app/shared/model/major.model';
import { IGrade } from 'app/shared/model/grade.model';
import { IStudent } from 'app/shared/model/student.model';
import { ISchoolClass } from 'app/shared/model/school-class.model';

export interface IException {
  id?: number;
  date?: Moment;
  isYouthLeague?: boolean;
  cause?: string;
  originalMajor?: IMajor;
  newMajor?: IMajor;
  originalGrade?: IGrade;
  newGrade?: IGrade;
  student?: IStudent;
  originalSchoolClass?: ISchoolClass;
  newSchoolClass?: ISchoolClass;
}

export class Exception implements IException {
  constructor(
    public id?: number,
    public date?: Moment,
    public isYouthLeague?: boolean,
    public cause?: string,
    public originalMajor?: IMajor,
    public newMajor?: IMajor,
    public originalGrade?: IGrade,
    public newGrade?: IGrade,
    public student?: IStudent,
    public originalSchoolClass?: ISchoolClass,
    public newSchoolClass?: ISchoolClass
  ) {
    this.isYouthLeague = this.isYouthLeague || false;
  }
}
