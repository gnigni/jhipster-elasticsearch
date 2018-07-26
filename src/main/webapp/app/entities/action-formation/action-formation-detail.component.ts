import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActionFormation } from 'app/shared/model/action-formation.model';

@Component({
    selector: 'jhi-action-formation-detail',
    templateUrl: './action-formation-detail.component.html'
})
export class ActionFormationDetailComponent implements OnInit {
    actionFormation: IActionFormation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actionFormation }) => {
            this.actionFormation = actionFormation;
        });
    }

    previousState() {
        window.history.back();
    }
}
