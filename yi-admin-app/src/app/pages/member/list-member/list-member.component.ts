import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MemberService} from '../../../services/member.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {MemberLevel} from "../../models/original/member-level.model";
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-member',
    templateUrl: './list-member.component.html',
    styleUrls: ['./list-member.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListMemberComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    memberLevel: Array<MemberLevel>;

    constructor(public route: ActivatedRoute, public router: Router, private memberService: MemberService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, memberService);
        this.buildForm();
    }

    ngOnInit() {
        this.goMemberLevel()
    }

    goMemberLevel() {
        this.getDatas();
        this.memberService.memberLevel().subscribe(response => {
            this.memberLevel = response;
        });
    }

    menus = [
        {name: "全部会员", code: 0, value: ""},
        {name: "在线会员", code: 1, value: 0},
        {name: "冻结会员", code: 2, value: 1},
    ];

    selectMenu: number = 0

    onItemClick(i) {
        this.pageQuery.clearFilter();
        let elementsByClassName: any = document.getElementsByClassName("filter");
        for (let elementsByClassNameKey in elementsByClassName) {
            elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
        }
        this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('state');
        this.getDatas();
        this.selectMenu = i;
    }

    buildForm() {
        this.searchForm = this.fb.group({
            id: [null],
            guid: [null],
            parentId: [null],
            username: [null],
            password: [null],
            nickname: [null],
            memberLevelId: [null],
            memberType: [null],
            province: [null],
            city: [null],
            district: [null],
            address: [null],
            createTime: [null],
            deleted: [null],
            delTime: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            id: null,
            guid: null,
            parentId: null,
            username: null,
            password: null,
            nickname: null,
            memberLevelId: null,
            memberType: null,
            province: null,
            city: null,
            district: null,
            address: null,
            createTime: null,
            deleted: null,
            delTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.username != null) {
            pageQuery.addOnlyFilter('username', searchObj.username, 'contains');
        }
        return pageQuery;
    }

    enable(memberId){
        this.dialogService.confirm('提示', '确认启用会员吗？').then((result) => {
            if (result) {
                this.memberService.prohibition(memberId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                      /*  this.router.navigate(['/pages/member/list']);*/
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
        }, () => {
        });
    }




    frozen(memberId){
        this.dialogService.confirm('提示', '确认冻结会员吗？').then((result) => {
            if (result) {
                this.memberService.prohibition(memberId).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        /*this.router.navigate(['/pages/member/list']);*/
                        this.getDatas();
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                   }, error => {
                    this.dialogService.alert('请求错误', error.message);
                });
            }
          }, () => {
        });
    }


}
