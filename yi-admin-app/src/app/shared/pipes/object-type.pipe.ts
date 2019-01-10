import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'objectType'
})
export class ObjectTypePipe implements PipeTransform {

    transform(value: any, args?: any): any {
        let v;
        switch (value) {

            case "ORDER":
                v = "订单";
                break;
            default:
                v = "未知";
                break;
        }
        return v;
    }

}
