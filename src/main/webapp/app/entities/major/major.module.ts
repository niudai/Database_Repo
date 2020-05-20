import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { MajorComponent } from './major.component';
import { MajorDetailComponent } from './major-detail.component';
import { MajorUpdateComponent } from './major-update.component';
import { MajorDeleteDialogComponent } from './major-delete-dialog.component';
import { majorRoute } from './major.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(majorRoute)],
  declarations: [MajorComponent, MajorDetailComponent, MajorUpdateComponent, MajorDeleteDialogComponent],
  entryComponents: [MajorDeleteDialogComponent],
})
export class JhipsterMajorModule {}
