import { Moment } from 'moment';
import { ISemaster } from 'app/shared/model/semaster.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IRecord {
  id?: number;
  date?: Moment;
  score?: number;
  semaster?: ISemaster;
  student?: IStudent;
}

export class Record implements IRecord {
  constructor(public id?: number, public date?: Moment, public score?: number, public semaster?: ISemaster, public student?: IStudent) {}
}
