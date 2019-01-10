import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {CommodityService} from "../../../services/commodity.service";
import {Commodity} from "../../../models/original/commodity.model";
import {Location} from "@angular/common";
import {FormGroup} from "@angular/forms";
import {EditorComponent} from "../../../components/editor/editor.component";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'view-commodity-to-examine',
  templateUrl: './view-commodity-to-examine.component.html',
})
export class ViewCommodityToExamineComponent implements OnInit {

  commodity: Commodity = new Commodity;

  commonForm: FormGroup;

  @ViewChild('editor') editor: EditorComponent;

  constructor(
    private route: ActivatedRoute,
    public msgSrv: NzMessageService,
    public http: _HttpClient,
    public msg: NzMessageService,
    private commodityService: CommodityService,
    private location: Location,
    public sanli:DomSanitizer
  ) { }

  descriptionContent="";

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });

  }

  getById(objId) {
    this.commodityService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.commodity = response.data;
        this.descriptionContent = this.commodity.description;
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

}
