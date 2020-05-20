import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { CampusComponent } from './campus.component';
import { CampusDetailComponent } from './campus-detail.component';
import { CampusUpdateComponent } from './campus-update.component';
import { CampusDeleteDialogComponent } from './campus-delete-dialog.component';
import { campusRoute } from './campus.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(campusRoute)],
  declarations: [CampusComponent, CampusDetailComponent, CampusUpdateComponent, CampusDeleteDialogComponent],
  entryComponents: [CampusDeleteDialogComponent],
})
export class JhipsterCampusModule {}
