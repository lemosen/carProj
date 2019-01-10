import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {Member} from "../../domain/original/member.model";
import {MemberProvider} from "../../services/member-service/member";
import {NativeProvider} from "../../services/native-service/native";
import {PageQuery} from "../../util/page-query.model";

@Component({
    selector: 'app-my-team',
    templateUrl: './my-team.page.html',
    styleUrls: ['./my-team.page.scss'],
})
export class MyTeamPage implements OnInit {
    level = 'memberLv1';
    memberList: Member[] = [];
    isLoading: boolean = false;
    pageQuery: PageQuery = new PageQuery();
    /**
     * 请求名,getMemberLv1,getMemberLv2
     */
    requestName = 'getMemberLv1';

    /**
     * 显示一级下级人数，
     */
    num1=0;
    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController,) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.pageQuery.pushParamsRequests('member.id', MemberProvider.getLoginMember().id);
        this.listFilter(this.level);
    }

    listFilter(type) {
        this.memberList = [];
        this.pageQuery.resetRequests();
        this.pageQuery.addSort('createTime','desc');
        if (type == 'memberLv1') this.requestName = 'getMemberLv1';
        this.getData();
    }

    private getData() {
        this.isLoading = true;
        this.memberProvider[this.requestName](this.pageQuery.requests).then(data => {
            this.memberList = data.content;
            this.pageQuery.covertResponses(data);
            if(this.requestName=='getMemberLv1') this.num1=this.pageQuery.responses.totalElements;
            this.isLoading = false;
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.isLoading = false;
        });
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.memberProvider[this.requestName](this.pageQuery.requests).then(data => {
                    this.memberList = this.memberList.concat(data.content);
                    this.pageQuery.covertResponses(data);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }



}
