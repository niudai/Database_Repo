import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { RecordComponent } from 'app/entities/record/record.component';
import { RecordService } from 'app/entities/record/record.service';
import { Record } from 'app/shared/model/record.model';

describe('Component Tests', () => {
  describe('Record Management Component', () => {
    let comp: RecordComponent;
    let fixture: ComponentFixture<RecordComponent>;
    let service: RecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [RecordComponent]
      })
        .overrideTemplate(RecordComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecordComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecordService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Record(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.records && comp.records[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
