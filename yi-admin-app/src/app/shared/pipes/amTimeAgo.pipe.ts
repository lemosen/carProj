import {Pipe, PipeTransform} from "@angular/core";

const ONE_MINUTE: number = 1000 * 60;
const ONE_HOUR: number = ONE_MINUTE * 60;
const ONE_DAY: number = ONE_HOUR * 24;
const ONE_WEEK: number = ONE_HOUR * 7;
const ONE_MONTH: number = ONE_DAY * 30;
const ONE_YEAR: number = ONE_DAY * 365;

const units = [ONE_YEAR, ONE_MONTH, ONE_WEEK, ONE_DAY, ONE_HOUR, ONE_MINUTE];
const names = ['年', '个月', '周', '天', '小时', '分钟'];

@Pipe({
    name: 'amTimeAgoPipe'
})
export class AmTimeAgoPipe implements PipeTransform {
    constructor() {
    }

    transform(value: string) {
        if (value) {
            value = this.getDateDiff(value)
        }
        return value;
    }

    getDateDiff(pTime: string) {
        let now = new Date().getTime();
        let old = new Date(pTime).getTime();
        let diff = now - old;

        for (let index = 0; index < units.length; ++index) {
            let val = diff / units[index];
            if (val >= 1) {
                return Math.round(val) + names[index] + '前';
            }
        }

        return "刚刚";
    }

}
