import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { SemasterComponent } from './semaster.component';
import { SemasterDetailComponent } from './semaster-detail.component';
import { SemasterUpdateComponent } from './semaster-update.component';
import { SemasterDeleteDialogComponent } from './semaster-delete-dialog.component';
import { semasterRoute } from './semaster.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(semasterRoute)],
  declarations: [SemasterComponent, SemasterDetailComponent, SemasterUpdateComponent, SemasterDeleteDialogComponent],
  entryComponents: [SemasterDeleteDialogComponent],
})
export class JhipsterSemasterModule {}
