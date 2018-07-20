/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterElasticsearchTestModule } from '../../../test.module';
import { ElasticQueryDetailComponent } from 'app/entities/elastic-query/elastic-query-detail.component';
import { ElasticQuery } from 'app/shared/model/elastic-query.model';

describe('Component Tests', () => {
    describe('ElasticQuery Management Detail Component', () => {
        let comp: ElasticQueryDetailComponent;
        let fixture: ComponentFixture<ElasticQueryDetailComponent>;
        const route = ({ data: of({ elasticQuery: new ElasticQuery(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterElasticsearchTestModule],
                declarations: [ElasticQueryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ElasticQueryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ElasticQueryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.elasticQuery).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
