import {CommonList} from "../../../shared/common/common-list";
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {PlatformSaleStatService} from "../../../services/platform-sale-stat.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DialogsService} from "../../components/dialogs/dialogs.service";
import {FormGroup} from "@angular/forms";
import {PlatformSaleStat} from "../../models/original/platform-sale-stat.model";

@Component({
    selector: 'app-list-platform_sale_stat',
    templateUrl: './list-platform_sale_stat.component.html',
    styleUrls: ['./list-platform_sale_stat.component.scss'],
    encapsulation: ViewEncapsulation.None
})

export class ListPlatformSaleStat extends CommonList implements OnInit {
    searchForm: FormGroup;

    constructor(public route: ActivatedRoute, public router: Router, private platformSaleStatService: PlatformSaleStatService, public dialogService: DialogsService) {
        super(route, router, dialogService, platformSaleStatService);
    }

    statistics:PlatformSaleStat;

    ngOnInit() {
        this.goplatformSaleStatController();
       /* this.getDatas();*/
    }

    startTimes:String;

    endTimes:String;



    goplatformSaleStatController(){
        this.platformSaleStatService.search(this.startTimes,this.endTimes).subscribe(response => {
            this.statistics=response.data;
        });
    }

    startTime(event) {
        // console.log(event.year+"-"+event.month+"-"+event.day);
        this.startTimes=event.year+"-"+event.month+"-"+event.day
    }
    clear(){
        this.startTimes="",
        this.endTimes=""
        this.goplatformSaleStatController();
    }

    endTime(event) {
        // console.log(event.year+"-"+event.month+"-"+event.day);
            this.endTimes=event.year+"-"+event.month+"-"+event.day
    }

}
