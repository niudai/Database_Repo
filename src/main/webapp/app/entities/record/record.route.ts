import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecord, Record } from 'app/shared/model/record.model';
import { RecordService } from './record.service';
import { RecordComponent } from './record.component';
import { RecordDetailComponent } from './record-detail.component';
import { RecordUpdateComponent } from './record-update.component';

@Injectable({ providedIn: 'root' })
export class RecordResolve implements Resolve<IRecord> {
  constructor(private service: RecordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((record: HttpResponse<Record>) => {
          if (record.body) {
            return of(record.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Record());
  }
}

export const recordRoute: Routes = [
  {
    path: '',
    component: RecordComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.record.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RecordDetailComponent,
    resolve: {
      record: RecordResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.record.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RecordUpdateComponent,
    resolve: {
      record: RecordResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.record.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RecordUpdateComponent,
    resolve: {
      record: RecordResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.record.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
