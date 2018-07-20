import { NgModule } from '@angular/core';

import { JhipsterElasticsearchSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhipsterElasticsearchSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [JhipsterElasticsearchSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipsterElasticsearchSharedCommonModule {}
