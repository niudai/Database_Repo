import { Moment } from 'moment';
import { ITeacher } from 'app/shared/model/teacher.model';
import { IStudent } from 'app/shared/model/student.model';
import { IdType } from 'app/shared/model/enumerations/id-type.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPeople {
  id?: number;
  idType?: IdType;
  chineseName?: string;
  englishName?: string;
  gender?: Gender;
  birthDate?: Moment;
  race?: string;
  nation?: string;
  address?: string;
  postcode?: string;
  telephone?: string;
  teacher?: ITeacher;
  student?: IStudent;
}

export class People implements IPeople {
  constructor(
    public id?: number,
    public idType?: IdType,
    public chineseName?: string,
    public englishName?: string,
    public gender?: Gender,
    public birthDate?: Moment,
    public race?: string,
    public nation?: string,
    public address?: string,
    public postcode?: string,
    public telephone?: string,
    public teacher?: ITeacher,
    public student?: IStudent
  ) {}
}
