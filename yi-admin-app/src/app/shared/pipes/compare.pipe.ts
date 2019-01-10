import {Pipe, PipeTransform} from '@angular/core';
import {ObjectUtils} from "../utils/ObjectUtils";

@Pipe({
    name: 'compare'
})
export class ComparePipe implements PipeTransform {

    transform(sourceObj: any, targetObj: any): boolean {
        console.log("sourceObj=" + JSON.stringify(sourceObj) + " targetObj=" + JSON.stringify(targetObj))

        let result = ObjectUtils.compare(sourceObj, targetObj);

        console.log("result=" + JSON.stringify(result))
        return result;
    }

}
