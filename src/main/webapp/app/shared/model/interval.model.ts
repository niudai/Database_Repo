import { ICourse } from 'app/shared/model/course.model';
import { WeekDay } from 'app/shared/model/enumerations/week-day.model';

export interface IInterval {
  id?: number;
  day?: WeekDay;
  start?: number;
  end?: number;
  course?: ICourse;
}

export class Interval implements IInterval {
  constructor(public id?: number, public day?: WeekDay, public start?: number, public end?: number, public course?: ICourse) {}
}
