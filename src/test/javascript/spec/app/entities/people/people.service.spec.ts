import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PeopleService } from 'app/entities/people/people.service';
import { IPeople, People } from 'app/shared/model/people.model';
import { IdType } from 'app/shared/model/enumerations/id-type.model';

describe('Service Tests', () => {
  describe('People Service', () => {
    let injector: TestBed;
    let service: PeopleService;
    let httpMock: HttpTestingController;
    let elemDefault: IPeople;
    let expectedResult: IPeople | IPeople[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PeopleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new People(
        0,
        IdType.IdCard,
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            birthDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a People', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate
          },
          returnedFromService
        );

        service.create(new People()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a People', () => {
        const returnedFromService = Object.assign(
          {
            idType: 'BBBBBB',
            chineseName: 'BBBBBB',
            englishName: 'BBBBBB',
            gender: 1,
            birthDate: currentDate.format(DATE_FORMAT),
            race: 'BBBBBB',
            nation: 'BBBBBB',
            address: 'BBBBBB',
            postcode: 'BBBBBB',
            telephone: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of People', () => {
        const returnedFromService = Object.assign(
          {
            idType: 'BBBBBB',
            chineseName: 'BBBBBB',
            englishName: 'BBBBBB',
            gender: 1,
            birthDate: currentDate.format(DATE_FORMAT),
            race: 'BBBBBB',
            nation: 'BBBBBB',
            address: 'BBBBBB',
            postcode: 'BBBBBB',
            telephone: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a People', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
