import {CommonList} from '../../../shared/common/common-list';
import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {CategoryService} from '../../../services/category.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PageQuery} from '../../models/page-query.model';
import {SUCCESS} from "../../models/constants.model";
import {Category} from "../../models/original/category.model";
import {Role} from "../../models/original/role.model";
import {ObjectUtils} from "../../../shared/utils/ObjectUtils";

@Component({
    selector: 'app-list-category',
    templateUrl: './list-category.component.html',
    styleUrls: ['./list-category.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListCategoryComponent extends CommonList implements OnInit {

    searchForm: FormGroup;

    //categoryChoose="请选择"

    category: Category;

    @ViewChild('tree') tree

    @ViewChild('fileUploader') fileUploader;

    constructor(public route: ActivatedRoute, public router: Router, public categoryService: CategoryService, public dialogService: DialogsService, private fb: FormBuilder) {
        super(route, router, dialogService, categoryService);
        this.buildForm();
    }



    imgPathPic="";

    getPic(event) {
        this.imgPathPic=event[0].url;
        // this.attachments=event;
    }


    /*ngOnChanges(value) {
        if (value.category !== undefined && value.category.currentValue !== undefined) {
            this.setBuildFormValue(this.category);
        }
    }
    setBuildFormValue(category: Category) {
        this.searchForm.setValue({
            id: category,
            categoryNo:category.categoryNo,
            categoryName:category.categoryName,
            imgPath: category.imgPath,
            remark: category.remark,
        });
    }*/


    /*  categoryName: category,*/
   /* http://127.0.0.1:8020/wanbohuizhi/Station_building_scheme.html*/

    open() {
        this.tree.open();
    }
   /* id=""*/
    setParentCategory(event) {
        if (event.id != 0) {
            this.categoryService.getById(event.id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.category = response.data;
                    if(this.category.parent==null){
                        this.searchForm.patchValue({
                            categoryName:this.category.categoryName,
                            categoryNo:this.category.categoryNo,
                            imgPath: this.category.imgPath,
                            remark: this.category.remark,
                        })
                    }else{
                        this.searchForm.patchValue({
                            parent:{
                                id:this.category.parent.id,
                                categoryName:this.category.parent.categoryName
                            },
                             categoryName:this.category.categoryName,
                             categoryNo:this.category.categoryNo,
                             imgPath: this.category.imgPath,
                             remark: this.category.remark,
                        })
                    }

                    this.getSelect(this.category.id,1)
                } else {
                    this.dialogService.toast('请求失败', response.message);
                }
            }, error => {
                this.dialogService.toast('请求错误', error.message);
            });
        }
    }

    ngOnInit() {
        this.getDatas();
    }

    formErrors = {

        parent: [

        ],
        categoryNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大16位长度',
            }
        ],
        categoryName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        imgPath: [
            {
                name: '',
                msg: '',
            }
        ],
        sort: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大3位长度',
            }
        ],
        remark: [
            {
                name: 'maxlength',
                msg: '最大127位长度',
            }
        ],
        name:[

        ],
    };



    buildForm():void{
        this.searchForm = this.fb.group({
            name:[],
            parent: [],
            categoryNo: [  null, Validators.compose([Validators.required,
                Validators.maxLength(128)
            ])],
            categoryName: [  null, Validators.compose([Validators.required,
                Validators.maxLength(128)
            ])],
            imgPath: [],
            sort: [  null, Validators.compose([Validators.required,
                Validators.maxLength(128)
            ])],
            remark: [null],
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
            parent: {
                id: null,
                categoryName: null
            },
            categoryNo: null,
            categoryName: null,
            imgPath: null,
            sort: null,
            remark: null,
        });
        this.pageQuery.clearFilter();
        // this.categoryChoose="请选择";
        this.searchData();
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.parent.categoryName != null){
            pageQuery.addOnlyFilter('parent.categoryName', searchObj.parent.categoryName, 'contains');
        }
        if (searchObj.categoryNo != null) {
            pageQuery.addOnlyFilter('categoryNo', searchObj.categoryNo, 'contains');
        }
        if (searchObj.categoryName != null) {
            pageQuery.addOnlyFilter('categoryName', searchObj.categoryName, 'contains');
        }
        return pageQuery;
    }

    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.searchForm);
        console.log(this.searchForm.value)
        if (commonFormValid) {
            return this.searchForm.value;
        }
        return null;
    }
    submitted = true
    updateCategory() {
        const searchObj = this.searchForm.value;
        if(this.imgPathPic!=""){
            searchObj.imgPath=this.imgPathPic;
        }
        console.log(searchObj)
        searchObj.id=this.category.id;
        console.log(searchObj)
        console.log("---"+searchObj)
        console.log("==="+this.category)
        if (!searchObj) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        this.dialogService.confirm('提示', '确认要提交吗？').then((result) => {
            if (result) {
                this.submitted = true;
                this.categoryService.update(searchObj).subscribe(response => {
                    if (response.result == SUCCESS) {
                        this.dialogService.toast();
                        this.router.navigate(['/pages/category/list']);
                    } else {
                        this.dialogService.alert('请求失败', response.message);
                    }
                    this.submitted = false;
                }, error => {
                    this.dialogService.alert('请求错误', error.message);
                    this.submitted = false;
                });
            }
        }, () => {
            this.submitted = false;
        });
    }


    superior(event){
        this.searchForm.patchValue({
            parent:{
                id:event.id,
                categoryName:event.name
            },
            name:event.name,
        })

    }

}
