/**
 * 字符串中间段隐藏保密 ****
 */
import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
    name: 'secretPipe'
})
export class SecretPipe implements PipeTransform {
    constructor() {
    }

    transform(value: string, start: number, secretLength: number) {
        if (value == null || start == null || secretLength == null
            || value.length < start
        ) {
            return value;
        }

        return value.substring(0, start) + "****" + value.substring(start + secretLength);
    }
}
