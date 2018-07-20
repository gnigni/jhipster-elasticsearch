import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ElasticQuery } from 'app/shared/model/elastic-query.model';
import { ElasticQueryService } from './elastic-query.service';
import { ElasticQueryComponent } from './elastic-query.component';
import { ElasticQueryDetailComponent } from './elastic-query-detail.component';
import { ElasticQueryUpdateComponent } from './elastic-query-update.component';
import { ElasticQueryDeletePopupComponent } from './elastic-query-delete-dialog.component';
import { IElasticQuery } from 'app/shared/model/elastic-query.model';

@Injectable({ providedIn: 'root' })
export class ElasticQueryResolve implements Resolve<IElasticQuery> {
    constructor(private service: ElasticQueryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((elasticQuery: HttpResponse<ElasticQuery>) => elasticQuery.body));
        }
        return of(new ElasticQuery());
    }
}

export const elasticQueryRoute: Routes = [
    {
        path: 'elastic-query',
        component: ElasticQueryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ElasticQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elastic-query/:id/view',
        component: ElasticQueryDetailComponent,
        resolve: {
            elasticQuery: ElasticQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ElasticQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elastic-query/new',
        component: ElasticQueryUpdateComponent,
        resolve: {
            elasticQuery: ElasticQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ElasticQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elastic-query/:id/edit',
        component: ElasticQueryUpdateComponent,
        resolve: {
            elasticQuery: ElasticQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ElasticQueries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const elasticQueryPopupRoute: Routes = [
    {
        path: 'elastic-query/:id/delete',
        component: ElasticQueryDeletePopupComponent,
        resolve: {
            elasticQuery: ElasticQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ElasticQueries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
