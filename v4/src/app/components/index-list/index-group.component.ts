import {Component, Input, ViewChild} from '@angular/core';
import {ItemDivider} from "@ionic/angular";

/**
 * Generated class for the IndexGroupComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
    selector: 'index-group',
    templateUrl: './index-group.component.html',
    styleUrls: ['./index-group.component.scss']
})
export class IndexGroupComponent {

    @Input() index: string = 'A';
    @Input() headColor: string = 'light';
    @ViewChild('header') header: ItemDivider;

    constructor() {

    }

    getOffsetTop() {
        // return this.header.getNativeElement().offsetTop;
    }

}
