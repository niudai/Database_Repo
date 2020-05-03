import { IPeople } from 'app/shared/model/people.model';
import { ISchoolClass } from 'app/shared/model/school-class.model';
import { ITeacher } from 'app/shared/model/teacher.model';
import { IException } from 'app/shared/model/exception.model';
import { ICourse } from 'app/shared/model/course.model';
import { ICampus } from 'app/shared/model/campus.model';

export interface IMajor {
  id?: number;
  name?: string;
  manager?: IPeople;
  schoolClasses?: ISchoolClass[];
  teachers?: ITeacher[];
  exceptions?: IException[];
  exceptions?: IException[];
  courses?: ICourse[];
  campus?: ICampus;
}

export class Major implements IMajor {
  constructor(
    public id?: number,
    public name?: string,
    public manager?: IPeople,
    public schoolClasses?: ISchoolClass[],
    public teachers?: ITeacher[],
    public exceptions?: IException[],
    public exceptions?: IException[],
    public courses?: ICourse[],
    public campus?: ICampus
  ) {}
}
