import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {MemberRoutingModule} from './member-routing.module';
import {ListMemberComponent} from './list-member/list-member.component';
import {AddMemberComponent} from './add-member/add-member.component';
import {EditMemberComponent} from './edit-member/edit-member.component';
import {FormMemberComponent} from './form-member/form-member.component';
import {ViewMemberComponent} from './view-member/view-member.component';
import {ComponentsModule} from "../../components/components.module";
import {MemberService} from '../../services/member.service';
import {MemberLevelService} from "../../services/member-level.service";
import {PipesModule} from "../../pipes/pipes.module";

const COMPONENTS = [
  ListMemberComponent,
  AddMemberComponent,
  EditMemberComponent,
  FormMemberComponent,
  ViewMemberComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
MemberRoutingModule,
    ComponentsModule,
    PipesModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[MemberService,MemberLevelService]
})
export class MemberModule { }
