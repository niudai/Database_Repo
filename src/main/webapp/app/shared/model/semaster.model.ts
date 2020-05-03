import { ICourse } from 'app/shared/model/course.model';
import { IRecord } from 'app/shared/model/record.model';
import { Season } from 'app/shared/model/enumerations/season.model';

export interface ISemaster {
  id?: number;
  year?: number;
  season?: Season;
  courses?: ICourse[];
  records?: IRecord[];
}

export class Semaster implements ISemaster {
  constructor(public id?: number, public year?: number, public season?: Season, public courses?: ICourse[], public records?: IRecord[]) {}
}
