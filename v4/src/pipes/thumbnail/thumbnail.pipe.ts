import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'thumbnailPipe'
})
export class ThumbnailPipe implements PipeTransform {

    /**
     *
     * @param value
     * @param {string} size  图片规格大小
     * @returns {any}
     */
    transform(value: any, size: string = "normal"): any {
        //   let num = value.lastIndexOf(".");
        //   let string = value.slice(0, num);
        //   let postfix = value.slice(num);
        // switch (size){
        //     case "normal":
        //       return value;
        //     case "s":
        //       return string + "_200_200_s" + postfix;
        //     case "m":
        //       return string + "_750_540_m" + postfix;
        //     case "l":
        //       return string + "_750_750_l" + postfix;
        // }
        if (!value) {
            return null;
        }
        let postfix = "";
        switch (size) {
            case "normal":
                return value;
            case "s":
                return value + "_300_300_s" + postfix;
            case "m":
                return value + "_750_650_m" + postfix;
            case "l":
                return value + "_800_800_l" + postfix;
        }

        // switch (size){
        //     case "normal":
        //       return value;
        //     case "s":
        //       return value + "_200_200_s" + postfix;
        //     case "m":
        //       return value + "_750_540_m" + postfix;
        //     case "l":
        //       return value + "_750_750_l" + postfix;
        // }
    }
}
