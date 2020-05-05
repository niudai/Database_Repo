import { Season } from 'app/shared/model/enumerations/season.model';

export interface ISemaster {
  id?: number;
  year?: number;
  season?: Season;
}

export class Semaster implements ISemaster {
  constructor(public id?: number, public year?: number, public season?: Season) {}
}
