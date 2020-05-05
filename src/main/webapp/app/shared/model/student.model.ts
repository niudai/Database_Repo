import { IJException } from 'app/shared/model/j-exception.model';
import { IRecord } from 'app/shared/model/record.model';
import { IPeople } from 'app/shared/model/people.model';
import { ISchoolClass } from 'app/shared/model/school-class.model';

export interface IStudent {
  id?: number;
  studentNumber?: string;
  startDate?: string;
  email?: string;
  major?: string;
  exceptions?: IJException[];
  records?: IRecord[];
  people?: IPeople;
  schoolClass?: ISchoolClass;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public studentNumber?: string,
    public startDate?: string,
    public email?: string,
    public major?: string,
    public exceptions?: IJException[],
    public records?: IRecord[],
    public people?: IPeople,
    public schoolClass?: ISchoolClass
  ) {}
}
