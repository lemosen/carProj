import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {CommentService} from "../../components/comment/comment.service";
import {MemberService} from "../../../services/member.service";
import {SUCCESS} from "../../models/constants.model";

@Component({
    selector: 'app-list-comment',
    templateUrl: './list-comment.component.html',
    styleUrls: ['./list-comment.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListCommentComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    constructor(public memberService: MemberService, public route: ActivatedRoute, public router: Router, private commentService: CommentService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, commentService);
        this.buildForm();
    }

    ngOnInit() {
        this.getDatas();
    }

    buildForm() {
        this.searchForm = this.fb.group({
            commodity: {
                id: null,
                commodityName: null,
            },
            commodityName: [null],
            username:[null],
            member: {
                id: null,
                username: null
            },
            nickname: [null],
            serialNo: [null],
            commentStar: [null],
            commentContent: [null],
            replyContent: [null],
            display: [null],
            commentTime: [null],
            replyTime: [null],
        });
    }

    searchData() {
        this.configPageQuery(this.pageQuery);
        this.getDatas();
    }

    clearSearch() {
        this.searchForm.reset({
            commodity: {
                id: null,
                commodityName: null,
            },
            commodityName: null,
            username: null,
            member: {
                id: null,
                username: null
            },
            nickname: null,
            serialNo: null,
            commentStar: null,
            commentContent: null,
            replyContent: null,
            display: null,
            commentTime: null,
            replyTime: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.commodityName != null) {
            pageQuery.addOnlyFilter('commodityName', searchObj.commodityName, 'contains');
        }
        if (searchObj.username != null) {
            pageQuery.addOnlyFilter('member.username', searchObj.username, 'contains');
        }
        return pageQuery;
    }

    replys:string[]=[""];
    /**
     * 回复
     */
    reply(id,e){
        console.log(e)
        if(!e){
            this.dialogService.toast("warning","提示","回复内容不能为空")
            return
        }
        this.commentService.reply(id,e).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dialogService.toast();
              /*  this.router.navigate(['/pages/comment/list']);*/
                this.getDatas();
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }

    updateDispaly(id,display){
        this.commentService.updateDispaly(id,display).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dialogService.toast();
                this.getDatas();
            } else {
                this.dialogService.alert('请求失败', response.message);
            }
        }, error => {
            this.dialogService.alert('请求错误', error.message);
        });
    }


}
