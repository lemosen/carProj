import {Pipe, PipeTransform, SecurityContext} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Pipe({name: 'htmlToPlaintext'})
export class HtmlToPlaintextPipe implements PipeTransform {

    constructor(private sanitizer: DomSanitizer) {
    }

    transform(value: string, args: any[] = []) {
        if (value == null) return value;

        return this.sanitizer.sanitize(SecurityContext.HTML, value);
    }
}
