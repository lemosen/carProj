import {Component, EventEmitter, Input, OnDestroy, Output, ViewEncapsulation} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

declare var $: any;

@Component({
    selector: 'app-editor',
    templateUrl: './editor.component.html',
    encapsulation: ViewEncapsulation.None
})
export class EditorComponent implements OnDestroy {
    @Input()
    ckeditorContent: any;
    @Input()
    height = 300;
    @Output()
    result = new EventEmitter

    constructor(private sanitizer: DomSanitizer) {
        let _this = this
        $(document).ready(function () {

            $('#summernote').summernote({
                airPopover: [
                    ['color', ['color']],
                    ['font', ['bold', 'underline', 'clear']],
                    ['para', ['ul', 'paragraph']],
                    ['table', ['table']],
                    ['insert', ['link', 'picture']]
                ],
                height: _this.height,
                callbacks: {
                    onBlur: function () {
                        _this.saveAndPush();

                    }

                }

            });
        });

    }

    saveAndPush() {
        this.ckeditorContent = this.sanitizer.bypassSecurityTrustHtml($('#summernote').summernote('code'));
        this.result.emit(this.ckeditorContent.changingThisBreaksApplicationSecurity)
    }

    setContent(content: string) {
        $('#summernote').summernote('code',content);
    }

    ngOnDestroy(): void {
    }


}
