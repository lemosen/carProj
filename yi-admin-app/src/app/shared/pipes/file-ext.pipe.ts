import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'fileExt'
})
export class FileExtPipe implements PipeTransform {

    transform(value: any, args?: any): any {
        let v;
        switch (value) {
            case "doc":
                v = "fa-file-archive-o text-success";
                break;
            case "excel":
                v = "fa-file-audio-o text-danger";
                break;
            case "txt":
                v = "fa-file-movie-o text-warning";
                break;
            default:
                v = "fa-file-image-o text-primary";
                break;
        }
        return v;
    }

}
