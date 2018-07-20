/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterElasticsearchTestModule } from '../../../test.module';
import { ElasticQueryComponent } from 'app/entities/elastic-query/elastic-query.component';
import { ElasticQueryService } from 'app/entities/elastic-query/elastic-query.service';
import { ElasticQuery } from 'app/shared/model/elastic-query.model';

describe('Component Tests', () => {
    describe('ElasticQuery Management Component', () => {
        let comp: ElasticQueryComponent;
        let fixture: ComponentFixture<ElasticQueryComponent>;
        let service: ElasticQueryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterElasticsearchTestModule],
                declarations: [ElasticQueryComponent],
                providers: []
            })
                .overrideTemplate(ElasticQueryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ElasticQueryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElasticQueryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ElasticQuery(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.elasticQueries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
