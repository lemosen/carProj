import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {CommunityService} from "../../../services/community.service";
import {Community} from "../../../models/original/community.model";
import {Location} from "@angular/common";
import {DomSanitizer} from "@angular/platform-browser";
import {MemberService} from "../../../services/member.service";
import {PageQuery} from "../../../models/page-query.model";

@Component({
  selector: 'view-community',
  templateUrl: './view-community.component.html',
  styleUrls: ['./view-community.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCommunityComponent implements OnInit {

  community: Community = new Community;

  loading = false;
  pageQuery: PageQuery = new PageQuery();
  datas: any[] = [];

  constructor(private route: ActivatedRoute, private location: Location, private communityService: CommunityService,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, public sanli: DomSanitizer, private memberService: MemberService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
      this.pageQuery.addOnlyOneFilter("community.id", params["objId"], "eq");
    });
    this.getData();
  }

  getById(objId) {
    this.communityService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.community = response.data;
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

  goBack() {
    this.location.back();
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value)
    this.searchData();
  }

  searchData(reset: boolean = false): void {
    if (reset) {
      this.pageQuery.resetPage();
    }
    this.getData();
  }

  getData() {
    this.loading = true;
    this.memberService.query(this.pageQuery).subscribe(response => {
      this.datas = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  chooseSwitch(i) {
    let state =  this.datas[i].state, id =  this.datas[i].id;
    if (state == 0) {
      //禁用
      this.memberService.prohibition(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("禁用成功");
          this.datas[i].state=1
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    } else if (state == 1) {
      //启用
      this.memberService.prohibition(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("启用成功");
          this.datas[i].state=0
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    }
  }

  remove(memberId) {
    this.memberService.removeById(memberId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("删除成功");
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }

}
