import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { SchoolClassComponent } from './school-class.component';
import { SchoolClassDetailComponent } from './school-class-detail.component';
import { SchoolClassUpdateComponent } from './school-class-update.component';
import { SchoolClassDeleteDialogComponent } from './school-class-delete-dialog.component';
import { schoolClassRoute } from './school-class.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(schoolClassRoute)],
  declarations: [SchoolClassComponent, SchoolClassDetailComponent, SchoolClassUpdateComponent, SchoolClassDeleteDialogComponent],
  entryComponents: [SchoolClassDeleteDialogComponent]
})
export class JhipsterSchoolClassModule {}
