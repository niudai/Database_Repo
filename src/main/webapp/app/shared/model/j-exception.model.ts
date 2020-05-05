import { Moment } from 'moment';
import { IMajor } from 'app/shared/model/major.model';
import { ISchoolClass } from 'app/shared/model/school-class.model';
import { IGrade } from 'app/shared/model/grade.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IJException {
  id?: number;
  date?: Moment;
  isYouthLeague?: boolean;
  cause?: string;
  originalMajor?: IMajor;
  newMajor?: IMajor;
  originalSchoolClass?: ISchoolClass;
  newSchoolClass?: ISchoolClass;
  originalGrade?: IGrade;
  newGrade?: IGrade;
  student?: IStudent;
}

export class JException implements IJException {
  constructor(
    public id?: number,
    public date?: Moment,
    public isYouthLeague?: boolean,
    public cause?: string,
    public originalMajor?: IMajor,
    public newMajor?: IMajor,
    public originalSchoolClass?: ISchoolClass,
    public newSchoolClass?: ISchoolClass,
    public originalGrade?: IGrade,
    public newGrade?: IGrade,
    public student?: IStudent
  ) {
    this.isYouthLeague = this.isYouthLeague || false;
  }
}
