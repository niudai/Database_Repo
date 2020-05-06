import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecord } from 'app/shared/model/record.model';

type EntityResponseType = HttpResponse<IRecord>;
type EntityArrayResponseType = HttpResponse<IRecord[]>;

@Injectable({ providedIn: 'root' })
export class RecordService {
  public resourceUrl = SERVER_API_URL + 'api/records';

  constructor(protected http: HttpClient) {}

  create(record: IRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(record);
    return this.http
      .post<IRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(record: IRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(record);
    return this.http
      .put<IRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(record: IRecord): IRecord {
    const copy: IRecord = Object.assign({}, record, {
      date: record.date && record.date.isValid() ? record.date.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((record: IRecord) => {
        record.date = record.date ? moment(record.date) : undefined;
      });
    }
    return res;
  }
}
