import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { JExceptionComponent } from './j-exception.component';
import { JExceptionDetailComponent } from './j-exception-detail.component';
import { JExceptionUpdateComponent } from './j-exception-update.component';
import { JExceptionDeleteDialogComponent } from './j-exception-delete-dialog.component';
import { jExceptionRoute } from './j-exception.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(jExceptionRoute)],
  declarations: [JExceptionComponent, JExceptionDetailComponent, JExceptionUpdateComponent, JExceptionDeleteDialogComponent],
  entryComponents: [JExceptionDeleteDialogComponent]
})
export class JhipsterJExceptionModule {}
