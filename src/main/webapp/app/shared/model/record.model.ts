import { Moment } from 'moment';
import { ISemaster } from 'app/shared/model/semaster.model';
import { ICourse } from 'app/shared/model/course.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IRecord {
  id?: number;
  date?: Moment;
  score?: number;
  semaster?: ISemaster;
  course?: ICourse;
  student?: IStudent;
}

export class Record implements IRecord {
  constructor(
    public id?: number,
    public date?: Moment,
    public score?: number,
    public semaster?: ISemaster,
    public course?: ICourse,
    public student?: IStudent
  ) {}
}
