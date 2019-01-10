import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ImgModifyShowComponent} from './img-modify-show/img-modify-show.component';

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [ImgModifyShowComponent],
    exports: [ImgModifyShowComponent]
})
export class ImgModifyModule {
}
