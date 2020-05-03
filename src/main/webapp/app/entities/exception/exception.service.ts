import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IException } from 'app/shared/model/exception.model';

type EntityResponseType = HttpResponse<IException>;
type EntityArrayResponseType = HttpResponse<IException[]>;

@Injectable({ providedIn: 'root' })
export class ExceptionService {
  public resourceUrl = SERVER_API_URL + 'api/exceptions';

  constructor(protected http: HttpClient) {}

  create(exception: IException): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exception);
    return this.http
      .post<IException>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(exception: IException): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exception);
    return this.http
      .put<IException>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IException>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IException[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(exception: IException): IException {
    const copy: IException = Object.assign({}, exception, {
      date: exception.date && exception.date.isValid() ? exception.date.toJSON() : undefined
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
      res.body.forEach((exception: IException) => {
        exception.date = exception.date ? moment(exception.date) : undefined;
      });
    }
    return res;
  }
}
