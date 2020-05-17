import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IMajor } from 'app/shared/model/major.model';

type EntityResponseType = HttpResponse<IMajor>;
type EntityArrayResponseType = HttpResponse<IMajor[]>;

@Injectable({ providedIn: 'root' })
export class MajorService {
  public resourceUrl = SERVER_API_URL + 'api/majors';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/majors';

  constructor(protected http: HttpClient) {}

  create(major: IMajor): Observable<EntityResponseType> {
    return this.http.post<IMajor>(this.resourceUrl, major, { observe: 'response' });
  }

  update(major: IMajor): Observable<EntityResponseType> {
    return this.http.put<IMajor>(this.resourceUrl, major, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMajor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMajor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMajor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
