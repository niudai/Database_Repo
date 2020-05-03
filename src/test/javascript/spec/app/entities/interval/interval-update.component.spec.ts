import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { IntervalUpdateComponent } from 'app/entities/interval/interval-update.component';
import { IntervalService } from 'app/entities/interval/interval.service';
import { Interval } from 'app/shared/model/interval.model';

describe('Component Tests', () => {
  describe('Interval Management Update Component', () => {
    let comp: IntervalUpdateComponent;
    let fixture: ComponentFixture<IntervalUpdateComponent>;
    let service: IntervalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [IntervalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IntervalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntervalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntervalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Interval(123);
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
        const entity = new Interval();
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
