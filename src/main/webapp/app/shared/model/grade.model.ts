import { ISchoolClass } from 'app/shared/model/school-class.model';
import { IException } from 'app/shared/model/exception.model';

export interface IGrade {
  id?: number;
  grade?: number;
  schoolClasses?: ISchoolClass[];
  exceptions?: IException[];
  exceptions?: IException[];
}

export class Grade implements IGrade {
  constructor(
    public id?: number,
    public grade?: number,
    public schoolClasses?: ISchoolClass[],
    public exceptions?: IException[],
    public exceptions?: IException[]
  ) {}
}
