import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListSupplierRecordComponent} from './list-supplier-record/list-supplier-record.component';
import {AddSupplierRecordComponent} from './add-supplier-record/add-supplier-record.component';
import {EditSupplierRecordComponent} from './edit-supplier-record/edit-supplier-record.component';
import {ViewSupplierRecordComponent} from './view-supplier-record/view-supplier-record.component';
import {SupplierWithdrawService} from '../../services/supplier-withdraw.service';


const routes: Routes = [
{path: '', redirectTo: 'list', pathMatch: 'full'},
    {path: 'list', component: ListSupplierRecordComponent, data: {breadcrumb: '列表'}},
    {path: 'add', component: AddSupplierRecordComponent, data: {breadcrumb: '新增'}},
    {path: 'edit/:objId', component: EditSupplierRecordComponent, data: {breadcrumb: '编辑'}},
    {path: 'view/:objId', component: ViewSupplierRecordComponent, data: {breadcrumb: '详情'}},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [SupplierWithdrawService],
})
export class SupplierRecordRoutingModule {
}
