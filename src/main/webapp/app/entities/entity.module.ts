import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterElasticsearchElasticQueryModule } from './elastic-query/elastic-query.module';
import { JhipsterElasticsearchActionFormationModule } from './action-formation/action-formation.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterElasticsearchElasticQueryModule,
        JhipsterElasticsearchActionFormationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterElasticsearchEntityModule {}
