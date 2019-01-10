import {Pipe, PipeTransform} from '@angular/core';
import {ObjectUtils} from "../utils/ObjectUtils";

@Pipe({
    name: 'isEmpty'
})
export class IsEmptyPipe implements PipeTransform {

    transform(value: any, args?: any): any {
        return ObjectUtils.isEmpty(value);
    }

}
