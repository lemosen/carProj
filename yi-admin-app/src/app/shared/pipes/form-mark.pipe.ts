import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'formMark',
    pure: false
})
export class FormMarkPipe implements PipeTransform {

    transform(form: any, args?: any): any {
        if (form) {
            return form.invalid && (form.dirty || form.touched);
        }
        return false;
    }

}
