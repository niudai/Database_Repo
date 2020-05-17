import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISemaster } from 'app/shared/model/semaster.model';

type EntityResponseType = HttpResponse<ISemaster>;
type EntityArrayResponseType = HttpResponse<ISemaster[]>;

@Injectable({ providedIn: 'root' })
export class SemasterService {
  public resourceUrl = SERVER_API_URL + 'api/semasters';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/semasters';

  constructor(protected http: HttpClient) {}

  create(semaster: ISemaster): Observable<EntityResponseType> {
    return this.http.post<ISemaster>(this.resourceUrl, semaster, { observe: 'response' });
  }

  update(semaster: ISemaster): Observable<EntityResponseType> {
    return this.http.put<ISemaster>(this.resourceUrl, semaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISemaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISemaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISemaster[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
