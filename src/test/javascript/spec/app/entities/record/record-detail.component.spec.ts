import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { RecordDetailComponent } from 'app/entities/record/record-detail.component';
import { Record } from 'app/shared/model/record.model';

describe('Component Tests', () => {
  describe('Record Management Detail Component', () => {
    let comp: RecordDetailComponent;
    let fixture: ComponentFixture<RecordDetailComponent>;
    const route = ({ data: of({ record: new Record(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [RecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load record on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.record).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
