import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActionFormation } from 'app/shared/model/action-formation.model';
import { ActionFormationService } from './action-formation.service';
import { ActionFormationComponent } from './action-formation.component';
import { ActionFormationDetailComponent } from './action-formation-detail.component';
import { ActionFormationUpdateComponent } from './action-formation-update.component';
import { ActionFormationDeletePopupComponent } from './action-formation-delete-dialog.component';
import { IActionFormation } from 'app/shared/model/action-formation.model';

@Injectable({ providedIn: 'root' })
export class ActionFormationResolve implements Resolve<IActionFormation> {
    constructor(private service: ActionFormationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((actionFormation: HttpResponse<ActionFormation>) => actionFormation.body));
        }
        return of(new ActionFormation());
    }
}

export const actionFormationRoute: Routes = [
    {
        path: 'action-formation',
        component: ActionFormationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ActionFormations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-formation/:id/view',
        component: ActionFormationDetailComponent,
        resolve: {
            actionFormation: ActionFormationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActionFormations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-formation/new',
        component: ActionFormationUpdateComponent,
        resolve: {
            actionFormation: ActionFormationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActionFormations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action-formation/:id/edit',
        component: ActionFormationUpdateComponent,
        resolve: {
            actionFormation: ActionFormationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActionFormations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actionFormationPopupRoute: Routes = [
    {
        path: 'action-formation/:id/delete',
        component: ActionFormationDeletePopupComponent,
        resolve: {
            actionFormation: ActionFormationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ActionFormations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
