import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICampus } from 'app/shared/model/campus.model';

type EntityResponseType = HttpResponse<ICampus>;
type EntityArrayResponseType = HttpResponse<ICampus[]>;

@Injectable({ providedIn: 'root' })
export class CampusService {
  public resourceUrl = SERVER_API_URL + 'api/campuses';

  constructor(protected http: HttpClient) {}

  create(campus: ICampus): Observable<EntityResponseType> {
    return this.http.post<ICampus>(this.resourceUrl, campus, { observe: 'response' });
  }

  update(campus: ICampus): Observable<EntityResponseType> {
    return this.http.put<ICampus>(this.resourceUrl, campus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICampus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICampus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
