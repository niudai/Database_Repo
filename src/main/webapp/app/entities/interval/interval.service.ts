import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInterval } from 'app/shared/model/interval.model';

type EntityResponseType = HttpResponse<IInterval>;
type EntityArrayResponseType = HttpResponse<IInterval[]>;

@Injectable({ providedIn: 'root' })
export class IntervalService {
  public resourceUrl = SERVER_API_URL + 'api/intervals';

  constructor(protected http: HttpClient) {}

  create(interval: IInterval): Observable<EntityResponseType> {
    return this.http.post<IInterval>(this.resourceUrl, interval, { observe: 'response' });
  }

  update(interval: IInterval): Observable<EntityResponseType> {
    return this.http.put<IInterval>(this.resourceUrl, interval, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterval>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterval[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
