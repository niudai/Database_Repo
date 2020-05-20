import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICampus, Campus } from 'app/shared/model/campus.model';
import { CampusService } from './campus.service';
import { CampusComponent } from './campus.component';
import { CampusDetailComponent } from './campus-detail.component';
import { CampusUpdateComponent } from './campus-update.component';

@Injectable({ providedIn: 'root' })
export class CampusResolve implements Resolve<ICampus> {
  constructor(private service: CampusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICampus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((campus: HttpResponse<Campus>) => {
          if (campus.body) {
            return of(campus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Campus());
  }
}

export const campusRoute: Routes = [
  {
    path: '',
    component: CampusComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.campus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CampusDetailComponent,
    resolve: {
      campus: CampusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.campus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CampusUpdateComponent,
    resolve: {
      campus: CampusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.campus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CampusUpdateComponent,
    resolve: {
      campus: CampusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.campus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
