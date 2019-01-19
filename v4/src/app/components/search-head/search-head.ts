import {Component, Input, ViewChild} from '@angular/core';
import {Events, NavController} from "@ionic/angular";
import {LOCAL_CITY} from "../../Constants";
import {MemberProvider} from "../../../services/member-service/member";


/**
 * Generated class for the SearchHeadComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
    selector: 'search-head',
    templateUrl: 'search-head.html',
    styleUrls: ['./search-head.scss']
})
export class SearchHeadComponent {
    @Input()
    backgroundColor: string = "#ffffff";                     //头部搜索栏背景色

    @Input()
    isHome: boolean = false;


    @ViewChild('p') p;

    constructor(public events: Events, public memberProvider: MemberProvider, public navCtrl: NavController) {

    }

    goNews() {
        this.navCtrl.navigateForward("NewsPage");
    }

    goLocation() {
        this.navCtrl.navigateForward("LocationPage")
    }

    goSearch() {
        this.navCtrl.navigateForward("SearchResultPage")
    }


}
