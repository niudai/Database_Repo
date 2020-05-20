import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { SemasterUpdateComponent } from 'app/entities/semaster/semaster-update.component';
import { SemasterService } from 'app/entities/semaster/semaster.service';
import { Semaster } from 'app/shared/model/semaster.model';

describe('Component Tests', () => {
  describe('Semaster Management Update Component', () => {
    let comp: SemasterUpdateComponent;
    let fixture: ComponentFixture<SemasterUpdateComponent>;
    let service: SemasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SemasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SemasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SemasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SemasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Semaster(123);
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
        const entity = new Semaster();
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
