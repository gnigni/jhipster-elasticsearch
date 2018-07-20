/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterElasticsearchTestModule } from '../../../test.module';
import { ElasticQueryUpdateComponent } from 'app/entities/elastic-query/elastic-query-update.component';
import { ElasticQueryService } from 'app/entities/elastic-query/elastic-query.service';
import { ElasticQuery } from 'app/shared/model/elastic-query.model';

describe('Component Tests', () => {
    describe('ElasticQuery Management Update Component', () => {
        let comp: ElasticQueryUpdateComponent;
        let fixture: ComponentFixture<ElasticQueryUpdateComponent>;
        let service: ElasticQueryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterElasticsearchTestModule],
                declarations: [ElasticQueryUpdateComponent]
            })
                .overrideTemplate(ElasticQueryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ElasticQueryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElasticQueryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ElasticQuery(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.elasticQuery = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ElasticQuery();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.elasticQuery = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
