import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInterval, Interval } from 'app/shared/model/interval.model';
import { IntervalService } from './interval.service';
import { IntervalComponent } from './interval.component';
import { IntervalDetailComponent } from './interval-detail.component';
import { IntervalUpdateComponent } from './interval-update.component';

@Injectable({ providedIn: 'root' })
export class IntervalResolve implements Resolve<IInterval> {
  constructor(private service: IntervalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInterval> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((interval: HttpResponse<Interval>) => {
          if (interval.body) {
            return of(interval.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Interval());
  }
}

export const intervalRoute: Routes = [
  {
    path: '',
    component: IntervalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.interval.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IntervalDetailComponent,
    resolve: {
      interval: IntervalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.interval.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IntervalUpdateComponent,
    resolve: {
      interval: IntervalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.interval.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IntervalUpdateComponent,
    resolve: {
      interval: IntervalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.interval.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
