import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {SupplierRecordRoutingModule} from './supplier-record-routing.module';
import {ListSupplierRecordComponent} from './list-supplier-record/list-supplier-record.component';
import {AddSupplierRecordComponent} from './add-supplier-record/add-supplier-record.component';
import {EditSupplierRecordComponent} from './edit-supplier-record/edit-supplier-record.component';
import {FormSupplierRecordComponent} from './form-supplier-record/form-supplier-record.component';
import {ViewSupplierRecordComponent} from './view-supplier-record/view-supplier-record.component';
import {ComponentsModule} from "../../components/components.module";
import {SupplierWithdrawService} from '../../services/supplier-withdraw.service';

const COMPONENTS = [
  ListSupplierRecordComponent,
  AddSupplierRecordComponent,
  EditSupplierRecordComponent,
  FormSupplierRecordComponent,
  ViewSupplierRecordComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SupplierRecordRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SupplierWithdrawService]
})


export class SupplierRecordModule { }
