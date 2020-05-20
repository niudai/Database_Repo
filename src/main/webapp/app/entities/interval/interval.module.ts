import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { IntervalComponent } from './interval.component';
import { IntervalDetailComponent } from './interval-detail.component';
import { IntervalUpdateComponent } from './interval-update.component';
import { IntervalDeleteDialogComponent } from './interval-delete-dialog.component';
import { intervalRoute } from './interval.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(intervalRoute)],
  declarations: [IntervalComponent, IntervalDetailComponent, IntervalUpdateComponent, IntervalDeleteDialogComponent],
  entryComponents: [IntervalDeleteDialogComponent],
})
export class JhipsterIntervalModule {}
