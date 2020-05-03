import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMajor, Major } from 'app/shared/model/major.model';
import { MajorService } from './major.service';
import { MajorComponent } from './major.component';
import { MajorDetailComponent } from './major-detail.component';
import { MajorUpdateComponent } from './major-update.component';

@Injectable({ providedIn: 'root' })
export class MajorResolve implements Resolve<IMajor> {
  constructor(private service: MajorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMajor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((major: HttpResponse<Major>) => {
          if (major.body) {
            return of(major.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Major());
  }
}

export const majorRoute: Routes = [
  {
    path: '',
    component: MajorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.major.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MajorDetailComponent,
    resolve: {
      major: MajorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.major.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MajorUpdateComponent,
    resolve: {
      major: MajorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.major.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MajorUpdateComponent,
    resolve: {
      major: MajorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.major.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
