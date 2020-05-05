import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJException } from 'app/shared/model/j-exception.model';

type EntityResponseType = HttpResponse<IJException>;
type EntityArrayResponseType = HttpResponse<IJException[]>;

@Injectable({ providedIn: 'root' })
export class JExceptionService {
  public resourceUrl = SERVER_API_URL + 'api/j-exceptions';

  constructor(protected http: HttpClient) {}

  create(jException: IJException): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jException);
    return this.http
      .post<IJException>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jException: IJException): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jException);
    return this.http
      .put<IJException>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJException>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJException[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jException: IJException): IJException {
    const copy: IJException = Object.assign({}, jException, {
      date: jException.date && jException.date.isValid() ? jException.date.toJSON() : undefined
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
      res.body.forEach((jException: IJException) => {
        jException.date = jException.date ? moment(jException.date) : undefined;
      });
    }
    return res;
  }
}
