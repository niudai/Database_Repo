import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';
import { ISemaster } from 'app/shared/model/semaster.model';

export interface IRecord {
  id?: number;
  date?: Moment;
  score?: number;
  student?: IStudent;
  semaster?: ISemaster;
}

export class Record implements IRecord {
  constructor(public id?: number, public date?: Moment, public score?: number, public student?: IStudent, public semaster?: ISemaster) {}
}
