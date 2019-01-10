import {Pipe, PipeTransform} from '@angular/core';
import {StringUtils} from "../utils/StringUtils";

@Pipe({
    name: 'ellipsisString'
})
export class EllipsisStringPipe implements PipeTransform {

    constructor() {
    }

    transform(value: string, args?: number): string {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        if (value.length < args) {
            return value;
        }
        return value.substring(0, args) + '...';
    }
}
