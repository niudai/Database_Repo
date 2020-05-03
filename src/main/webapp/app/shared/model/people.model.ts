import { Moment } from 'moment';
import { ITeacher } from 'app/shared/model/teacher.model';
import { IStudent } from 'app/shared/model/student.model';
import { IMajor } from 'app/shared/model/major.model';
import { IdType } from 'app/shared/model/enumerations/id-type.model';

export interface IPeople {
  id?: number;
  idType?: IdType;
  chineseName?: string;
  englishName?: string;
  gender?: number;
  birthDate?: Moment;
  race?: string;
  nation?: string;
  address?: string;
  postcode?: string;
  telephone?: string;
  teacher?: ITeacher;
  student?: IStudent;
  manageMajor?: IMajor;
}

export class People implements IPeople {
  constructor(
    public id?: number,
    public idType?: IdType,
    public chineseName?: string,
    public englishName?: string,
    public gender?: number,
    public birthDate?: Moment,
    public race?: string,
    public nation?: string,
    public address?: string,
    public postcode?: string,
    public telephone?: string,
    public teacher?: ITeacher,
    public student?: IStudent,
    public manageMajor?: IMajor
  ) {}
}
