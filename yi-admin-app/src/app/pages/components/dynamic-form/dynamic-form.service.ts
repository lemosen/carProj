import {Injectable} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ObjectUtils} from "../../../shared/utils/ObjectUtils";

const isDate = ObjectUtils.isDate;
const isArray = ObjectUtils.isArray;
const isObject = ObjectUtils.isObject;
const isEmptyObject = ObjectUtils.isEmptyObject;
const isEmpty = ObjectUtils.isEmpty;

@Injectable()
export class DynamicFormService {

    constructor(private fb: FormBuilder) {

    }

    /**
     * 根据配置项生成FormGroup
     * @param options
     */
    transform(options: any []) {
        let group = {};
        options.map((option) => {
            if (option) {
                if (option.original) {
                    if (!isEmptyObject(option.control)) {
                        const nfb = this.fb.group({});
                        this.checkObj(option.control, nfb);
                        group[option.name] = nfb;
                        // group[option.name] = this.fb.group({'method': option.control.method, 'intervals': this.fb.array(option.control.intervals)});
                    } else {
                        group[option.name] = this.fb.group(option.control)
                    }

                } else if (option.originalArray) {
                    if (!isEmptyObject(option.control)) {
                        group[option.name] = this.fb.array(option.control.map(data => {
                            const nfb = this.fb.group({});
                            this.checkObj(data, nfb);
                            return nfb;
                        }));
                    } else {
                        group[option.name] = this.fb.array([]);
                    }

                } else {
                    if (option.controlType === 'group') {
                        group[option.name] = this.transform(option.control);
                    } else if (option.controlType === 'array') {
                        group[option.name] = this.fb.array(option.control);
                    } else {
                        group[option.name] = option.control;
                    }
                }
            }
        });

        let show = this.fb.group(group);
        return show;
    }

    checkObj(obj, fbGroup) {
        for (let key in obj) {
            const val = obj[key];

            // TODO: 20180425 恢复原有逻辑, 已经测试战略要素的增加修改成功, 但是否会导致其他问题需要测试确认
            if (val == null) {
                fbGroup.setControl(key, this.fb.control(null));
                continue;
            }

            if (isArray(val)) {
                fbGroup.setControl(key, this.fb.array(isEmpty(val) ? [] : val));

            } else if (isObject(val)) {
                fbGroup.setControl(key, this.fb.group(isEmpty(val) ? {} : val));

            } else {
                fbGroup.setControl(key, this.fb.control(isEmpty(val) ? null : val));
            }
        }
    }

}
