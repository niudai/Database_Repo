import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { ExceptionUpdateComponent } from 'app/entities/exception/exception-update.component';
import { ExceptionService } from 'app/entities/exception/exception.service';
import { Exception } from 'app/shared/model/exception.model';

describe('Component Tests', () => {
  describe('Exception Management Update Component', () => {
    let comp: ExceptionUpdateComponent;
    let fixture: ComponentFixture<ExceptionUpdateComponent>;
    let service: ExceptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExceptionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExceptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExceptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExceptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Exception(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Exception();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
