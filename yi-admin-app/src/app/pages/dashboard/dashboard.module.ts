import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {DashboardComponent} from './dashboard.component';
import {InfoPanelsComponent} from './info-panels/info-panels.component';
import {VisitorsComponent} from './visitors/visitors.component';
import {CostComponent} from './cost/cost.component';
import {InfoCardsComponent} from './info-cards/info-cards.component';
import {DiskSpaceComponent} from './disk-space/disk-space.component';
import {TodoComponent} from './todo/todo.component';
import {SharedDirectivesModule} from '../../shared/directives/shared-directives.module';

export const routes = [
    {path: '', component: DashboardComponent, pathMatch: 'full'}
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        FormsModule,
        PerfectScrollbarModule,
        NgxChartsModule,
        SharedDirectivesModule
    ],
    declarations: [
        DashboardComponent,
        InfoPanelsComponent,
        VisitorsComponent,
        CostComponent,
        InfoCardsComponent,
        DiskSpaceComponent,
        TodoComponent
    ]
})

export class DashboardModule {
}
