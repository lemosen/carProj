import {Component, Input, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-show-three-commodities',
    templateUrl: './show-three-commodities.component.html',
    styleUrls: ['./show-three-commodities.component.scss']
})
export class ShowThreeCommoditiesComponent implements OnInit {
    @Input()
    item;
    @Input()
    goCommodity
    constructor(public navCtrl: NavController,) {
    }

    ngOnInit() {
    }


}
