import { IMajor } from 'app/shared/model/major.model';

export interface ICampus {
  id?: number;
  name?: string;
  address?: string;
  majors?: IMajor[];
}

export class Campus implements ICampus {
  constructor(public id?: number, public name?: string, public address?: string, public majors?: IMajor[]) {}
}
