import {Pipe, PipeTransform} from '@angular/core';
import {ObjectUtils} from "../utils/ObjectUtils";

@Pipe({
    name: 'isNotEmpty'
})
export class IsNotEmptyPipe implements PipeTransform {

    transform(value: any, args?: any): any {
        return !ObjectUtils.isEmpty(value);
    }

}
