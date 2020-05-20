import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISchoolClass, SchoolClass } from 'app/shared/model/school-class.model';
import { SchoolClassService } from './school-class.service';
import { SchoolClassComponent } from './school-class.component';
import { SchoolClassDetailComponent } from './school-class-detail.component';
import { SchoolClassUpdateComponent } from './school-class-update.component';

@Injectable({ providedIn: 'root' })
export class SchoolClassResolve implements Resolve<ISchoolClass> {
  constructor(private service: SchoolClassService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchoolClass> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((schoolClass: HttpResponse<SchoolClass>) => {
          if (schoolClass.body) {
            return of(schoolClass.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SchoolClass());
  }
}

export const schoolClassRoute: Routes = [
  {
    path: '',
    component: SchoolClassComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.schoolClass.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SchoolClassDetailComponent,
    resolve: {
      schoolClass: SchoolClassResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.schoolClass.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SchoolClassUpdateComponent,
    resolve: {
      schoolClass: SchoolClassResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.schoolClass.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SchoolClassUpdateComponent,
    resolve: {
      schoolClass: SchoolClassResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.schoolClass.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
