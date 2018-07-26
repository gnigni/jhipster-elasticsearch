import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterElasticsearchSharedModule } from 'app/shared';
import {
    ActionFormationComponent,
    ActionFormationDetailComponent,
    ActionFormationUpdateComponent,
    ActionFormationDeletePopupComponent,
    ActionFormationDeleteDialogComponent,
    actionFormationRoute,
    actionFormationPopupRoute
} from './';

const ENTITY_STATES = [...actionFormationRoute, ...actionFormationPopupRoute];

@NgModule({
    imports: [JhipsterElasticsearchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActionFormationComponent,
        ActionFormationDetailComponent,
        ActionFormationUpdateComponent,
        ActionFormationDeleteDialogComponent,
        ActionFormationDeletePopupComponent
    ],
    entryComponents: [
        ActionFormationComponent,
        ActionFormationUpdateComponent,
        ActionFormationDeleteDialogComponent,
        ActionFormationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterElasticsearchActionFormationModule {}
