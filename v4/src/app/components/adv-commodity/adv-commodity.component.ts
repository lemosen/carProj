import {Component, Input, OnInit} from '@angular/core';
import {Commodity} from "../../../domain/original/commodity.model";
import {NavController} from "@ionic/angular";

@Component({
    selector: 'adv-commodity',
    templateUrl: './adv-commodity.component.html',
    styleUrls: ['./adv-commodity.component.scss']
})
export class AdvCommodityComponent implements OnInit {
    @Input()
    list;

    constructor(public navCtrl: NavController) {
    }

    ngOnInit() {
    }

    goCommodity(id) {
        this.navCtrl.navigateForward(["CommodityPage", {id: id}])
    }

}
