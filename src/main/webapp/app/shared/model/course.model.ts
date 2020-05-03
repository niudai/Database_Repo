import { IInterval } from 'app/shared/model/interval.model';
import { IMajor } from 'app/shared/model/major.model';
import { ITeacher } from 'app/shared/model/teacher.model';
import { ISemaster } from 'app/shared/model/semaster.model';
import { ExamType } from 'app/shared/model/enumerations/exam-type.model';

export interface ICourse {
  id?: number;
  name?: string;
  examType?: ExamType;
  times?: IInterval[];
  major?: IMajor;
  teacher?: ITeacher;
  semaster?: ISemaster;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public name?: string,
    public examType?: ExamType,
    public times?: IInterval[],
    public major?: IMajor,
    public teacher?: ITeacher,
    public semaster?: ISemaster
  ) {}
}
