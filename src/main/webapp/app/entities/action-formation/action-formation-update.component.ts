import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IActionFormation } from 'app/shared/model/action-formation.model';
import { ActionFormationService } from './action-formation.service';

@Component({
    selector: 'jhi-action-formation-update',
    templateUrl: './action-formation-update.component.html'
})
export class ActionFormationUpdateComponent implements OnInit {
    private _actionFormation: IActionFormation;
    isSaving: boolean;

    constructor(private actionFormationService: ActionFormationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ actionFormation }) => {
            this.actionFormation = actionFormation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.actionFormation.id !== undefined) {
            this.subscribeToSaveResponse(this.actionFormationService.update(this.actionFormation));
        } else {
            this.subscribeToSaveResponse(this.actionFormationService.create(this.actionFormation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IActionFormation>>) {
        result.subscribe((res: HttpResponse<IActionFormation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get actionFormation() {
        return this._actionFormation;
    }

    set actionFormation(actionFormation: IActionFormation) {
        this._actionFormation = actionFormation;
    }
}
