import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterElasticsearchSharedModule } from 'app/shared';
import {
    ElasticQueryComponent,
    ElasticQueryDetailComponent,
    ElasticQueryUpdateComponent,
    ElasticQueryDeletePopupComponent,
    ElasticQueryDeleteDialogComponent,
    elasticQueryRoute,
    elasticQueryPopupRoute
} from './';

const ENTITY_STATES = [...elasticQueryRoute, ...elasticQueryPopupRoute];

@NgModule({
    imports: [JhipsterElasticsearchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ElasticQueryComponent,
        ElasticQueryDetailComponent,
        ElasticQueryUpdateComponent,
        ElasticQueryDeleteDialogComponent,
        ElasticQueryDeletePopupComponent
    ],
    entryComponents: [
        ElasticQueryComponent,
        ElasticQueryUpdateComponent,
        ElasticQueryDeleteDialogComponent,
        ElasticQueryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterElasticsearchElasticQueryModule {}
