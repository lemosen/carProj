import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'dataType'
})
export class DataTypePipe implements PipeTransform {

    transform(value: any, args?: any): any {
        let v;
        switch (value) {
            case "NUMBER":
                v = "数量";
                break;
            case "MONEY":
                v = "金额";
                break;
            case "PERCENT":
                v = "百分比";
                break;
            case "SCALE":
                v = "等级";
                break;
            default:
                v = "未知";
                break;
        }
        return v;
    }

}
