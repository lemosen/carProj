import {Pipe, PipeTransform} from "@angular/core";
import {AppConfig} from '../../pages/configs/app-config';

/**
 * 头像管道, 如果没有头像时显示默认头像
 */
@Pipe({
    name: 'avatar'
})
export class AvatarPipe implements PipeTransform {
    constructor(private appConfig: AppConfig) {
    }

    transform(value: string): any {
        if (value == null || value.trim().length === 0) {
            return "/assets/images/s-hard.jpg";
        }

        // let fValue = this.appConfig.getImgBase() + value;
        return value;
    }
}
