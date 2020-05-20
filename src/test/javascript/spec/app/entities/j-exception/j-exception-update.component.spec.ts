import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { JExceptionUpdateComponent } from 'app/entities/j-exception/j-exception-update.component';
import { JExceptionService } from 'app/entities/j-exception/j-exception.service';
import { JException } from 'app/shared/model/j-exception.model';

describe('Component Tests', () => {
  describe('JException Management Update Component', () => {
    let comp: JExceptionUpdateComponent;
    let fixture: ComponentFixture<JExceptionUpdateComponent>;
    let service: JExceptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [JExceptionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JExceptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JExceptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JExceptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JException(123);
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
        const entity = new JException();
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
