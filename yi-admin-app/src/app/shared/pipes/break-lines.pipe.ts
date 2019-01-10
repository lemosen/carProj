import {Pipe, PipeTransform, SecurityContext} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Pipe({name: 'breakLines'})
export class BreakLinesPipe implements PipeTransform {

    constructor(private sanitizer: DomSanitizer) {
    }

    transform(value: any, args?: any): any {
        if (value == null || value.trim().length === 0) return "";

        let strArray = value.split(/(\r\n|\n|\r)/gm);
        let htmlArray = [];

        let str;
        for (let index = 0; index < strArray.length; ++index) {
            str = strArray[index];
            if (str.trim().length === 0 || str === "\n" || str === "\r" || str === "\r\n") continue;
            htmlArray.push(this.sanitizer.sanitize(SecurityContext.HTML, str));
        }

        return this.sanitizer.bypassSecurityTrustHtml(htmlArray.join("<br>"));
    }
}
