import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IException, Exception } from 'app/shared/model/exception.model';
import { ExceptionService } from './exception.service';
import { ExceptionComponent } from './exception.component';
import { ExceptionDetailComponent } from './exception-detail.component';
import { ExceptionUpdateComponent } from './exception-update.component';

@Injectable({ providedIn: 'root' })
export class ExceptionResolve implements Resolve<IException> {
  constructor(private service: ExceptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IException> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((exception: HttpResponse<Exception>) => {
          if (exception.body) {
            return of(exception.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Exception());
  }
}

export const exceptionRoute: Routes = [
  {
    path: '',
    component: ExceptionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.exception.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExceptionDetailComponent,
    resolve: {
      exception: ExceptionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.exception.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExceptionUpdateComponent,
    resolve: {
      exception: ExceptionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.exception.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExceptionUpdateComponent,
    resolve: {
      exception: ExceptionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.exception.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
