import { ISchoolClass } from 'app/shared/model/school-class.model';
import { ITeacher } from 'app/shared/model/teacher.model';
import { ICourse } from 'app/shared/model/course.model';
import { ICampus } from 'app/shared/model/campus.model';

export interface IMajor {
  id?: number;
  name?: string;
  schoolClasses?: ISchoolClass[];
  teachers?: ITeacher[];
  courses?: ICourse[];
  campus?: ICampus;
}

export class Major implements IMajor {
  constructor(
    public id?: number,
    public name?: string,
    public schoolClasses?: ISchoolClass[],
    public teachers?: ITeacher[],
    public courses?: ICourse[],
    public campus?: ICampus
  ) {}
}
