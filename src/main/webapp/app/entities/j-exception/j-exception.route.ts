import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJException, JException } from 'app/shared/model/j-exception.model';
import { JExceptionService } from './j-exception.service';
import { JExceptionComponent } from './j-exception.component';
import { JExceptionDetailComponent } from './j-exception-detail.component';
import { JExceptionUpdateComponent } from './j-exception-update.component';

@Injectable({ providedIn: 'root' })
export class JExceptionResolve implements Resolve<IJException> {
  constructor(private service: JExceptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJException> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jException: HttpResponse<JException>) => {
          if (jException.body) {
            return of(jException.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JException());
  }
}

export const jExceptionRoute: Routes = [
  {
    path: '',
    component: JExceptionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.jException.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JExceptionDetailComponent,
    resolve: {
      jException: JExceptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.jException.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JExceptionUpdateComponent,
    resolve: {
      jException: JExceptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.jException.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JExceptionUpdateComponent,
    resolve: {
      jException: JExceptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.jException.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
