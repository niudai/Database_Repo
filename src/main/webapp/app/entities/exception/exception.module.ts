import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { ExceptionComponent } from './exception.component';
import { ExceptionDetailComponent } from './exception-detail.component';
import { ExceptionUpdateComponent } from './exception-update.component';
import { ExceptionDeleteDialogComponent } from './exception-delete-dialog.component';
import { exceptionRoute } from './exception.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(exceptionRoute)],
  declarations: [ExceptionComponent, ExceptionDetailComponent, ExceptionUpdateComponent, ExceptionDeleteDialogComponent],
  entryComponents: [ExceptionDeleteDialogComponent]
})
export class JhipsterExceptionModule {}
