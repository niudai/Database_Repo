import { Moment } from 'moment';
import { ICourse } from 'app/shared/model/course.model';
import { ISchoolClass } from 'app/shared/model/school-class.model';
import { IPeople } from 'app/shared/model/people.model';
import { IMajor } from 'app/shared/model/major.model';
import { Title } from 'app/shared/model/enumerations/title.model';

export interface ITeacher {
  id?: number;
  workNumber?: string;
  startDate?: Moment;
  email?: string;
  title?: Title;
  courses?: ICourse[];
  schoolClass?: ISchoolClass;
  people?: IPeople;
  major?: IMajor;
}

export class Teacher implements ITeacher {
  constructor(
    public id?: number,
    public workNumber?: string,
    public startDate?: Moment,
    public email?: string,
    public title?: Title,
    public courses?: ICourse[],
    public schoolClass?: ISchoolClass,
    public people?: IPeople,
    public major?: IMajor
  ) {}
}
