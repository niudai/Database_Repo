import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISemaster, Semaster } from 'app/shared/model/semaster.model';
import { SemasterService } from './semaster.service';
import { SemasterComponent } from './semaster.component';
import { SemasterDetailComponent } from './semaster-detail.component';
import { SemasterUpdateComponent } from './semaster-update.component';

@Injectable({ providedIn: 'root' })
export class SemasterResolve implements Resolve<ISemaster> {
  constructor(private service: SemasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISemaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((semaster: HttpResponse<Semaster>) => {
          if (semaster.body) {
            return of(semaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Semaster());
  }
}

export const semasterRoute: Routes = [
  {
    path: '',
    component: SemasterComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.semaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SemasterDetailComponent,
    resolve: {
      semaster: SemasterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.semaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SemasterUpdateComponent,
    resolve: {
      semaster: SemasterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.semaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SemasterUpdateComponent,
    resolve: {
      semaster: SemasterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.semaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
