import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISchoolClass } from 'app/shared/model/school-class.model';

type EntityResponseType = HttpResponse<ISchoolClass>;
type EntityArrayResponseType = HttpResponse<ISchoolClass[]>;

@Injectable({ providedIn: 'root' })
export class SchoolClassService {
  public resourceUrl = SERVER_API_URL + 'api/school-classes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/school-classes';

  constructor(protected http: HttpClient) {}

  create(schoolClass: ISchoolClass): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schoolClass);
    return this.http
      .post<ISchoolClass>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(schoolClass: ISchoolClass): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schoolClass);
    return this.http
      .put<ISchoolClass>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISchoolClass>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISchoolClass[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISchoolClass[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(schoolClass: ISchoolClass): ISchoolClass {
    const copy: ISchoolClass = Object.assign({}, schoolClass, {
      createdDate: schoolClass.createdDate && schoolClass.createdDate.isValid() ? schoolClass.createdDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((schoolClass: ISchoolClass) => {
        schoolClass.createdDate = schoolClass.createdDate ? moment(schoolClass.createdDate) : undefined;
      });
    }
    return res;
  }
}
