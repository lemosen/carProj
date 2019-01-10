import {
    Component,
    EventEmitter,
    Input,
    OnChanges,
    OnInit,
    Output,
    ViewChild,
    ViewEncapsulation
} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {AbstractControl, FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Commodity} from '../../models/original/commodity.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';
import {SupplierService} from "../../../services/supplier.service";
import {FreightTemplateService} from "../../../services/freight-template.service";
import {Product} from "../../models/original/product.model";
import {SUCCESS} from "../../models/constants.model";
import {CategoryService} from "../../../services/category.service";
import {Category} from "../../models/original/category.model";
import {EditorComponent} from "../../components/editor/editor.component";
import {RegionService} from "../../../services/region.service";

@Component({
    selector: 'app-form-commodity',
    templateUrl: './form-commodity.component.html',
    styleUrls: ['./form-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormCommodityComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    attrArryForm: FormArray;

    attrGroup: FormGroup;

    public show: boolean = false;

    @Input() commodity: Commodity = new Commodity();

    category: Category;

    @ViewChild('tree') tree

    @Output()
    onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);

    @ViewChild('fileUploader') fileUploader;

    @ViewChild('editor') editor: EditorComponent;

    open() {
        this.tree.open();
    }

    addName() {
        this.show = false;
        let attr = this.commonForm.get("attributeGroups") as FormArray;
        let next = 9999
        if (attr.length != 0) {
            attr.get(attr.length - 1 + "").patchValue({
                next: attr.length
            })
        }
        let attributeNameaa = this.fb.group({
            id: [0],
            groupName: [''],
            lock: [false],
            attributes: this.fb.array([this.fb.group({
                    attrValue: ''
                })]
            ),
            next: next
        })
        if (this.attrArryForm.length == 0) {
            attr.push(attributeNameaa)
            this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray
        } else {
            attr.push(attributeNameaa)
            attr = this.commonForm.get("attributeGroups") as FormArray;
            if (attr.value[attr.length - 2].attrName == '') {
                attr.removeAt(this.attrArryForm.length - 1);
                this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray
                console.log(this.attrArryForm);
                this.dialogService.toast('warning', '提示', '请填写属性名');
                return;
            } else {
                attr = this.commonForm.get("attributeGroups") as FormArray;
                if (attr.value[attr.length - 2].attributes[0].attrValue == '') {
                    attr.removeAt(this.attrArryForm.length - 1);
                    this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray
                    console.log(this.attrArryForm);
                    this.dialogService.toast('warning', '提示', '请填写属性值');
                    return;
                }
            }
        }
    }

    addValue(i) {
        this.show = false;
        let attributeValues = this.fb.group({
            attrValue: '',
            id: 0
        });
        let attr = this.commonForm.get("attributeGroups") as FormArray;
        let newVar = attr.get(i + ".attributes") as FormArray;
        newVar.push(attributeValues)
        if (newVar.value[newVar.length - 2].attrValue == '') {
            newVar.removeAt(newVar.length - 1)
            this.dialogService.toast('warning', '提示', '属性值不能为空！');
        }
        this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray
    }

    /**
     * 删除属性名（属性值一起删除）
     * @param i（属性名下标）
     */
    deleteName(i) {
        this.show = false;
        let attr = this.commonForm.get("attributeGroups") as FormArray;
        attr.removeAt(i)
        this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray
    }

    /**
     * 删除属性值
     * @param i（属性名下标）
     * @param j（属性值下标）
     */
    deleteValue(i, j) {
        this.show = false;
        if(this.attrArryForm){
            let newProducts = this.commonForm.controls.products;
            for (let e of newProducts.value){
                let deleteAttrValue = this.attrArryForm.value[i].attributes[j].attrValue;
                if(deleteAttrValue==e.attrNames[i]){
                    e.id=null;
                }
            }
            this.attrArryForm.value[i].attributes[j].remove;
        }
        // if (i == 0) {
        //     this.dialogService.toast('warning', '提示', '至少有一条属性值！');
        //     return;
        // }
        let attr = this.commonForm.get("attributeGroups") as FormArray;
        let newVar = attr.get(i + ".attributes") as FormArray;
        if(newVar.length == 1){
            this.dialogService.toast('warning', '提示', '至少有一条属性值！');
            return;
        }
        newVar.removeAt(j)
        this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray
    }

    addTrue() {
        this.commonForm.setControl('products',this.fb.array([]))
        let attr = this.commonForm.get("attributeGroups") as FormArray;
            if (!attr.value[attr.length - 1]) {
                    this.dialogService.toast('warning', '提示', '请先添加商品属性！');
                    return;
        }
        if (attr.value[attr.length - 1]) {
            if (this.attrArryForm.value[0].groupName == '') {
                    this.dialogService.toast('warning', '提示', '请填写属性名');
                    return;
            } else {
                if (this.attrArryForm.value[0].attributes[0].attrValue == '') {
                    this.dialogService.toast('warning', '提示', '请填写属性值');
                    return;
                }
            }
        }
        if (attr.value[attr.length - 1].groupName == '') {
            attr.removeAt(attr.length - 1);
            this.show = true;
        } else {
            if (attr.value[attr.length - 1].attributes[0].attrValue == '') {
                this.dialogService.toast('warning', '提示', '属性值至少插入一条');
                return;
            } else {
                this.show = true;
            }
        }
        this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray;
        this.dynamicArray = ""
        this.createDy("", 0)
        let array = this.dynamicArray.split(";")
        console.log(this.dynamicArray);

        array.pop();//去除最后一个空值
        this.dynamicAttribute=[]
        this.dynamicAttribute = array.map(e => {
            return e.split(",")
        })
        console.log(this.dynamicAttribute);
        let formArryAttrValues: FormArray = this.commonForm.get('products') as FormArray;
        this.dynamicAttribute.forEach(e => {
            formArryAttrValues.push(this.fb.group({
                originalPrice: [0],
                currentPrice: [0],
                memberPrice: [0],
                stock: [0],
                attrNames: [e],
            }))
        })
        this.commonForm.setControl('products', formArryAttrValues);
        console.log(this.commonForm.controls)
    }

    dynamicAttribute = []

    dynamicArray = ""

    createDy(e1, i) {
        if (this.attrArryForm.value.length != i) {
            this.attrArryForm.value[i].attributes.forEach((e, index) => {
                if (this.attrArryForm.value[i].next != 0 && this.attrArryForm.value[i].next != 9999 && this.attrArryForm.value.length > this.attrArryForm.value[i].next) {
                    this.createDy(e1 + e.attrValue + ",", this.attrArryForm.value[i].next)
                } else {
                    this.dynamicArray += e1 + e.attrValue + ";"
                }
            })
        }
    }


    lock(form: AbstractControl) {
        form.patchValue({
            lock: true
        })
    }


    unLock(form: AbstractControl) {
        form.patchValue({
            lock: false
        })
    }

    formErrors = {

        commodityNo: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        commodityName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        commodityShortName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大64位长度',
            }
        ],
        imgPath: [
            {
                name: '',
                msg: '',
            }
        ],
        supplier: [
            {
                name: 'required',
                msg: '不可为空',
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
        distribution: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        commissionRate: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大4位长度',
            }
        ],
        freightSet: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        unifiedFreight: [
            {
                name: 'maxlength',
                msg: '最大15位长度',
            }
        ],
        freightTemplate: [
            {
                name: '',
                msg: '',
            }
        ],
        stockSet: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        shelf: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        description: [
            {
                name: 'required',
                msg: '不可为空',
            }
        ],
        attributeGroups: [{
            name: 'required',
            msg: '不可为空',
        }
        ],
        attrGroup: [{
            name: 'required',
            msg: '不可为空',
        }
        ],
        regions: [],
        attachmentVos:[],
    };

    imgPathPic="";
    // attachments=[];

    getPic(event) {
        this.imgPathPic=event[0].url;
        // this.attachments=event;
    }

    constructor(public regionService:RegionService, public freightTemplateService: FreightTemplateService,public categoryService: CategoryService, public supplierService: SupplierService, private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }


    ngOnChanges(value) {
        if (value.commodity !== undefined && value.commodity.currentValue !== undefined) {
            this.setBuildFormValue(this.commodity);
        }
        // this.createDy(this.addName(),this.addValue(this.addName()));
        // this.addName();
        // this.addValue(this.addName())
        // this.addTrue();
    }

    ngOnInit() {

    }

    setCommodityFreightTemplate(event) {
        this.commonForm.patchValue({
            freightTemplate: {
                id: event.id,
                templateName: event.templateName,
            }
        });
    }

    setCommoditySupplier(event) {
        this.commonForm.patchValue({
            supplier: {
                id: event.id,
                supplierName: event.supplierName,
            }
        });
    }
    select="请选择"
    setCommoditySaleRegion(event) {
        if(event==""){
            this.select="请选择"
        }
        this.commonForm.patchValue({
            regions: event.map(e => {
                this.select=event.province
                return {id: e.id,
                    province: e.province,
                    city: e.city}
            })
        });
    }

    setParentCategory(event) {
        this.commonForm.patchValue({
            categories: event.map(e => {
                return {
                    id: e.id,
                    categoryName: e.value
                    }
            })
        });
    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            commodityNo: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            commodityName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            commodityShortName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(64)
                ])
            ],
            supplier: this.fb.group({
                id: null,
                supplierName: [
                    "请选择",
                ]
            }),
            sort: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(3)
                ])
            ],
            imgPath: [],
            distribution: [
                null, Validators.compose([Validators.required
                ])
            ],
            commissionRate: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(4)
                ])
            ],
            freightSet: [
                null, Validators.compose([Validators.required
                ])
            ],
            unifiedFreight: [],
            freightTemplate: this.fb.group({
                id: null,
                templateName: [
                    "请选择",
                ]
            }),
            regions: [[]],
            categories:[[]],
            attachmentVos:[[]],
            stockSet: [
                null, Validators.compose([Validators.required
                ])
            ],
            shelf: [
                null, Validators.compose([Validators.required
                ])
            ],
            description:  [
                null, Validators.compose([Validators.required
                ])
            ],
            attributeGroups: this.fb.array([]),
            products: this.fb.array([]),
        });
        this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray
    }

    selectregionsName = '请选择'
    templateId = 0;
    templateN='请选择';
    setBuildFormValue(commodity: Commodity) {
        let dbCommodity: any = commodity.supplier;
        let commodityFT: any = commodity.freightTemplate;
        if(commodityFT != null){
            this.templateId = commodityFT.id;
        }
        if(commodityFT != null){
            this.templateN = commodityFT.templateName;
        }
        this.commonForm.setValue({
            commodityNo:
            commodity.commodityNo
            ,
            commodityName:
            commodity.commodityName
            ,
            commodityShortName:
            commodity.commodityShortName
            ,
            supplier: {
                id: dbCommodity.id,
                supplierName: dbCommodity.supplierName
            }
            ,
            sort:
            commodity.sort
            ,
            imgPath:
            commodity.imgPath
            ,
            distribution:
            commodity.distribution + ""
            ,
            commissionRate:
            commodity.commissionRate
            ,
            freightSet:
            commodity.freightSet + ""
            ,
            unifiedFreight:
            commodity.unifiedFreight
            ,
            freightTemplate: {
                id: this.templateId,
                templateName: this.templateN
            }
            ,
            regions:
            commodity.regions,
            categories:
            commodity.categories,
            stockSet:
            commodity.stockSet + ""
            ,
            shelf:
            commodity.shelf + ""
            ,
            description:
            commodity.description
            ,
            attributeGroups: []
            ,
            products: [],
            attachmentVos:[],
        });
        this.commonForm.value.imgPath=this.imgPathPic;
        this.descriptionContent = this.commonForm.value.description;

        //赋值富文本
        this.editor.setContent(commodity.description)

        if (commodity.attributeGroups.length != 0) {
            //排序获取的属性组
            commodity.attributeGroups.sort((e, e1) => {
                if (e.id > e1.id) {
                    return 1
                } else {
                    return -1
                }
            })
            //构建展示的属性组属性值表单
            commodity.attributeGroups.forEach((e, i) => {
                let attrGFormArray = this.commonForm.get('attributeGroups') as FormArray
                let attrVFormArray = this.fb.array([]) as FormArray
                //排序获取的属性值
                e.attributes.sort((e1, e2) => {
                    if (e1.id > e2.id) {
                        return 1
                    } else {
                        return -1
                    }
                })
                //构建属性值表单
                if (e.attributes.length != 0) {
                    e.attributes.forEach((e1, i1) => {
                        attrVFormArray.push(this.fb.group({
                            attrValue: e1.attrValue,
                            id: e1.id
                        }))
                    })
                }
                //构建属性组表单及层级关系
                if (i == commodity.attributeGroups.length - 1) {
                    attrGFormArray.push(this.fb.group({
                        id: [e.id],
                        groupName: [e.groupName],
                        lock: [true],
                        next: 9999,
                        attributes: attrVFormArray
                    }))
                }
                else {
                    attrGFormArray.push(this.fb.group({
                        id: [e.id],
                        groupName: [e.groupName],
                        lock: [true],
                        next: i + 1,
                        attributes: attrVFormArray
                    }))

                }

            })
            this.attrArryForm = this.commonForm.get("attributeGroups") as FormArray
            console.log(this.attrArryForm);
        }
        //获取生成表格属性值组表单  product表单
        this.dynamicArray = ""
        this.createDy("", 0)
        let array = this.dynamicArray.split(";")
        array.pop();//去除最后一个空值
        this.dynamicAttribute = array.map(e => {
            return e.split(",")
        })
        let formArryAttrValues: FormArray = this.commonForm.get('products') as FormArray;
        //对应product
        let products = []
        this.dynamicAttribute.forEach(e => {
            //判断用 临时product
            let tempProducts: Product[] = []
            e.forEach((e1, i1) => {
                commodity.attributeGroups.forEach((e2, i2) => {
                    e2.attributes.forEach((e3, i3) => {
                        if (e3.attrValue == e1) {
                            //将属性值相同的所有product添加到 临时product
                            tempProducts.push(...e3.products)
                        }
                    })
                })

            })
            //根据商品出现次数锁定该属性值组所对应product添加到product
            products.push(tempProducts.map(e5 => e5.id).map(e6 => {
                return {
                    count: tempProducts.filter(e7 => e7.id == e6).length,
                    product: tempProducts.filter(e7 => e7.id == e6)[0],
                    id: e6,
                    attrNames: e,
                }
            }).sort((e8, e9) => e8.count > e9.count ? -1 : 1)[0])

        })
        //返回参数叠加显示
        if (this.commonForm.value.regions.length>0) {
            this.selectregionsName="";
            this.commonForm.value.regions.forEach((e, index) => {
                if (this.commonForm.value.regions.length != index + 1) {
                    this.selectregionsName += e.city + ","
                } else {
                    this.selectregionsName += e.city
                }
            })
        }
        if(products.length >0){
            //构建product表单数据
            products.forEach(e => {
                formArryAttrValues.push(this.fb.group({
                    id: [e.product.id],
                    originalPrice: [e.product.originalPrice],
                    currentPrice: [e.product.currentPrice],
                    memberPrice: [e.product.memberPrice],
                    stock: [e.product.stock],
                    attrNames: [e.attrNames],
                }))
            })
            this.show = true
            this.commonForm.setControl('products', formArryAttrValues);
        }

    }

    descriptionContent = "";

    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
        if (commonFormValid) {
            return this.commonForm.value;
        }
        return null;
    }

    onSubmit() {
        let attrGroup = this.commonForm.get("attrValue") as FormArray;
        const formValue = this.submitCheck();
        if(this.imgPathPic!=""){
            formValue.imgPath=this.imgPathPic;
        }
        // formValue.attachmentVos=this.attachments;
        if (this.commodity) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        if(this.commonForm.value.sort>127){
            this.dialogService.toast('warning', '提示', '排序最大值为127！');
            return;
        }
        if (this.commonForm.get("supplier.supplierName").value == "请选择") {
            this.dialogService.toast('warning', '提示', '请选择供应商！');
            return;
        }
        if (this.commonForm.value.freightSet == 1) {
            if (this.commonForm.get("unifiedFreight").value == null) {
                this.dialogService.toast('warning', '提示', '请输入统一运费！');
                return;
            }
        }
        if (this.commonForm.value.freightSet == 2) {
            if (this.commonForm.get("freightTemplate.templateName").value == "请选择") {
                this.dialogService.toast('warning', '提示', '请选择运费模板！');
                return;
            }
        }
        if(this.commonForm.value.categories.length == 0){
            this.dialogService.toast('warning', '提示', '请选择商品所属分类！');
            return;
        }
        if(this.commonForm.value.regions.length == 0){
            this.dialogService.toast('warning', '提示', '请选择商品销售地！');
            return;
        }
        if (this.attrArryForm.controls.length == 0) {
            this.dialogService.toast('warning', '提示', '请添加商品属性！');
            return;
        }
        if (this.attrArryForm.controls.length != 0) {
            if (this.attrArryForm.value[0].groupName == '') {
                this.dialogService.toast('warning', '提示', '请填写属性名');
                return;
            } else {
                if (this.attrArryForm.value[0].attributes[0].attrValue == '') {
                    this.dialogService.toast('warning', '提示', '请填写属性值');
                    return;
                }
            }
        }
        /*console.log("commonForm value=" + JSON.stringify(formValue));*/
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

    thematicText(event) {
        this.commonForm.patchValue({
            description: event
        });
    }
}
